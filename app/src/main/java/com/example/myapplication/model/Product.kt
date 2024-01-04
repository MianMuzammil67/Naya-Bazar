package com.example.myapplication.model

data class Product (


        val productId: Int,
        val productName: String,
        val productDescription: String,
        val brand: String,
        val price: Double,
        val salePrice: Double?,
        val quantityInStock: Int,
        val stockStatus: String, // "In Stock", "Out of Stock", etc.
        val productImages: List<String>,
        val size: String?,
        val color: String?,
        val productCategory: String,
        val productTags: List<String>,
        val weight: Double,
        val length: Double,
        val width: Double,
        val height: Double,
        val publishedStatus: Boolean,
        val visibility: String, // "Public", "Private", etc.
        val customerReviews: List<String>, // Assuming review content as strings
        val averageRating: Double,
        val relatedProducts: List<String>,
        val discountPercentage: Double?,
        val discountAmount: Double?
    )
data class product (
        val productName:String = "",
        val productId:String = "",
        val productPrice:String = "",
        val productDes:String = "",
        val productRating: Float = 0.0F,
        val productDisCount:String = "",
        val productHave:Boolean = false,
        val productBrand:String = "",
        val productImage:String = "",
        val productCategory:String = "",
        val productNote:String = "",
)
