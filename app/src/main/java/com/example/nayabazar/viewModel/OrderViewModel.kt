package com.example.nayabazar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nayabazar.model.Order
import com.example.nayabazar.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: OrderRepository):ViewModel() {


    fun placeOrder(order : Order) =viewModelScope.launch {
        repository.placeOrder(order)
    }


}