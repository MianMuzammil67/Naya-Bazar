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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nayabazar.R
import com.example.nayabazar.databinding.FragmentCartBinding
import com.example.nayabazar.model.Product
import com.example.nayabazar.utils.Constants
import com.example.nayabazar.utils.Resource
import com.example.nayabazar.view.Adapter.CartAdapter
import com.example.nayabazar.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        val cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]

        cartAdapter = CartAdapter(cartViewModel)
        setRecyclerView()


//           if (cartAdapter.itemCount == 0){
//               binding.recyclerView.visibility = View.GONE
//               binding.emptyCartImage.visibility = View.VISIBLE
//
//           }else{
//               binding.recyclerView.visibility = View.VISIBLE
//               binding.emptyCartImage.visibility = View.GONE
//
//           }
        var r: List<Product> = emptyList()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.cartItemFlow.collect { response ->
                    collectCartItems(response)
                    response.data.let { data ->
                        if (data != null) {
                            r = data
                        }
                    }
                }
            }
        }
        var price = ""
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.productPrice.collectLatest { priceSum ->
                    priceSum.let {
                        price = String.format("%.2f", priceSum)
                        binding.tvPrice.text = "$ $price"
                    }
                }
            }
        }
        binding.btnCheckOut.setOnClickListener {
            val action =
                CartFragmentDirections.actionCartFragmentToCheckOutFragment(r.toTypedArray())
            findNavController().navigate(action)
        }
    }

    private fun collectCartItems(response: Resource<List<Product>>) {
        when (response) {
            is Resource.Success -> {
                Constants.hideProgressBar(binding.progressBar)
                cartAdapter.differ.submitList(response.data)
            }

            is Resource.Loading -> {
                Constants.showProgressBar(binding.progressBar)
            }

            is Resource.Error -> {
                Constants.hideProgressBar(binding.progressBar)
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                Log.d("CartItemFlow", response.message.toString())
            }
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context)
        }
        cartAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                super.onChanged()
                updateEmptyCartVisibility()
            }
        })
    }
//    private fun updateEmptyCartVisibility() {
//        val isEmpty = cartAdapter.itemCount == 0
//        binding.recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
//        binding.emptyCartImage.visibility = if (isEmpty) View.VISIBLE else View.GONE
//    }

    private fun updateEmptyCartVisibility() {
        if (cartAdapter.itemCount == 0) {
            binding.recyclerView.visibility = View.GONE
            binding.emptyCartImage.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyCartImage.visibility = View.GONE
        }
    }
}