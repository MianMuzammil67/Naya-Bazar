package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Product
import com.example.myapplication.model.SaleProduct
import com.example.myapplication.repository.ProductRepository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val productFlow : StateFlow<Resource<List<Product>>>
        get() {return repository.productFlow}
    val saleProductFlow : StateFlow<Resource<List<SaleProduct>>>
        get() {return repository.saleProduct}

    fun getAllProducts() = viewModelScope.launch {
        repository.getAllProducts()
    }
    fun getSaleProducts() = viewModelScope.launch {
        repository.getSaleProducts()
    }




}