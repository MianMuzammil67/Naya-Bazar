package com.example.nayabazar.model

data class Order(
    val product: List<Product>,
    val address: Address,
    val payment: Payment
)
