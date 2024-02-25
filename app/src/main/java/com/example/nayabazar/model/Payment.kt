package com.example.nayabazar.model

data class Payment(
    var cashOnDelivery : Boolean = true,
    var cardHolderName : String = "",
    var cardNumber: String = "",
    var expiryDate : String= "",
    var cardVerificationValue : String = ""

    )