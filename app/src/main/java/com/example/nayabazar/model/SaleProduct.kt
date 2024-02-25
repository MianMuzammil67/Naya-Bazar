package com.example.nayabazar.model

data class SaleProduct(
    val id: Int?,
    val name: String?,
    val image : String?
){
    // Default (no-argument) constructor
    constructor() : this(0, "", "")
}