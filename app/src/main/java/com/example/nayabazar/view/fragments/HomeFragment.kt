package com.example.nayabazar.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentHomeBinding
import com.example.nayabazar.model.Category
import com.example.nayabazar.model.Product
import com.example.nayabazar.model.SaleProduct
import com.example.nayabazar.utils.Constants
import com.example.nayabazar.utils.Resource
import com.example.nayabazar.view.Adapter.ProductAdapter
import com.example.nayabazar.view.Adapter.categoryAdapter
import com.example.nayabazar.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: categoryAdapter
    private lateinit var productAdapter: ProductAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupCategoryRecyclerView()
        setupProductRecyclerView()

        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        binding.carousel.registerLifecycle(lifecycle)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saleProductFlow.collect { list ->
                    setDataOnSlider(list)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryFlow.collect { category ->
                    setCategory(category)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productFlow.collect { products ->
                    setProducts(products)

                }
            }
        }
        categoryAdapter.clickListener { clickedCategory ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToProductFragment(clickedCategory.name)
            findNavController().navigate(action)
//            findNavController().navigate(R.id.action_homeFragment_to_productFragment)
        }
        productAdapter.clickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(action)
//            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }

    }
    private fun setProducts(products: Resource<List<Product>>) {
        when (products) {
            is Resource.Success -> {
                Constants.hideProgressBar(binding.progressBar)
                products.data.let {
                    productAdapter.differ.submitList(it)
                }
            }
            is Resource.Loading -> {
                Constants.showProgressBar(binding.progressBar)
            }
            is Resource.Error -> {
                Toast.makeText(context, products.message, Toast.LENGTH_LONG).show()
                Log.d("HomeFragment", products.message.toString())
            }
        }
    }
    private fun setCategory(category: Resource<List<Category>>) {
        when (category) {
            is Resource.Success -> {
                Constants.hideProgressBar(binding.progressBar)
                category.data.let {
                    categoryAdapter.differ.submitList(it)
                }
            }

            is Resource.Loading -> {
                Constants.showProgressBar(binding.progressBar)
            }

            is Resource.Error -> {
                Toast.makeText(context, category.message, Toast.LENGTH_LONG).show()
                Log.d("HomeFragment", category.message.toString())
            }
        }
    }

    private fun setDataOnSlider(list: Resource<List<SaleProduct>>) {
        when (list) {
            is Resource.Success -> {
                Constants.hideProgressBar(binding.progressBar)
                list.data.let {
                    val saleProducts: List<SaleProduct>? = it
                    val carouselItems: List<CarouselItem> = saleProducts?.map { saleProduct ->
                        CarouselItem(saleProduct.image, saleProduct.name)
                    } ?: emptyList()
                    binding.carousel.setData(carouselItems)
                }
            }

            is Resource.Loading -> {
                Constants.showProgressBar(binding.progressBar)
            }

            is Resource.Error -> {
                Toast.makeText(context, list.message, Toast.LENGTH_LONG).show()
                Log.d("HomeFragment", list.message.toString())
            }

        }

    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = categoryAdapter()
        binding.categoriesList.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = categoryAdapter
        }
    }

    private fun setupProductRecyclerView() {
        productAdapter = ProductAdapter()
        binding.productList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }
    }


}