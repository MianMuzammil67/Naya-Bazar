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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentProductBinding
import com.example.nayabazar.model.Product
import com.example.nayabazar.utils.Constants
import com.example.nayabazar.utils.Resource
import com.example.nayabazar.view.Adapter.ProductAdapter
import com.example.nayabazar.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_product) {
    private lateinit var binding: FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter
    private val args: ProductFragmentArgs by navArgs()
    private lateinit var categoryName: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)
        categoryName = args.CategoryName

        val actionBar = requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = categoryName

        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        if (categoryName.isEmpty()) {
            Toast.makeText(context, "SomeThing went Wrong", Toast.LENGTH_LONG).show()
        } else {
            viewModel.getCategoryProduct(categoryName)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryProduct.collect {
                    sendDataToRecyclerView(it)
                }
            }
        }
        setupProductRecyclerView()
    }

    private fun sendDataToRecyclerView(list: Resource<List<Product>>) {
        when (list) {
            is Resource.Success -> {
                Constants.hideProgressBar(binding.progressBar)
                list.data.let {
                    productAdapter.differ.submitList(it)
                }
            }

            is Resource.Loading -> {
                Constants.showProgressBar(binding.progressBar)
            }

            is Resource.Error -> {
                Toast.makeText(context, list.message, Toast.LENGTH_LONG).show()
                Log.d("ProductFragment", list.message.toString())
            }
        }
        productAdapter.clickListener {
            val action = ProductFragmentDirections.actionProductFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setupProductRecyclerView() {
        productAdapter = ProductAdapter()
        binding.rvProduct.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = productAdapter
        }
    }
}