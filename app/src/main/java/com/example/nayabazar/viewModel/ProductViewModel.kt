package com.example.nayabazar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nayabazar.model.Category
import com.example.nayabazar.model.Product
import com.example.nayabazar.model.SaleProduct
import com.example.nayabazar.repository.ProductRepository
import com.example.nayabazar.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
init {
    getSaleProducts()
    getCategories()
    getAllProducts()
}
    val productFlow : StateFlow<Resource<List<Product>>> = repository.productFlow
    val saleProductFlow : StateFlow<Resource<List<SaleProduct>>> = repository.saleProductFlow
    val categoryFlow :StateFlow<Resource<List<Category>>> = repository.categoryFlow
    val categoryProduct : StateFlow<Resource<List<Product>>> = repository.catProductFlow

    private fun getAllProducts() = viewModelScope.launch {
        repository.getAllProducts()
    }
    private fun getSaleProducts() = viewModelScope.launch {
        repository.getSaleProducts()
    }
    private fun getCategories() = viewModelScope.launch {
        repository.getCategories()
    }

    fun getCategoryProduct (categoryName : String)= viewModelScope.launch {
        repository.getCategoryProduct(categoryName)
    }
}