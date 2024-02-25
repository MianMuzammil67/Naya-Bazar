package com.example.nayabazar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nayabazar.model.Product
import com.example.nayabazar.repository.CartRepository
import com.example.nayabazar.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : ViewModel() {
    init {
        getCartItems()
    }

    val cartItemFlow: StateFlow<Resource<List<Product>>> = repository.cartItemFlow

    fun addToCart(product: Product) = viewModelScope.launch {
        repository.addToCart(product)
    }
    fun updateCart(product: Product, quantity: Int) = viewModelScope.launch {
        repository.updateCart(product, quantity)
    }
    private fun getCartItems() = viewModelScope.launch {
        repository.getFromCart()
    }
    fun deleteFromCart(product: Product) = viewModelScope.launch {
        repository.deleteFromCart(product)
    }  fun emptyCart() = viewModelScope.launch {
        repository.emptyCart()
    }

    val productPrice = cartItemFlow.map {
        when (it) {
            is Resource.Success -> {
                calculatePrice(it.data!!)
            }
            else -> null
        }
    }

    private fun calculatePrice(it: List<Product>): Float {
        return it.sumByDouble {
            it.productPrice.replace("$","").toFloat() * it.productQuantity.toInt().toDouble()
        }.toFloat()
    }


}
