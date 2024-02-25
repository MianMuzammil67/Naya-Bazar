package com.example.nayabazar.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(

    val productName: String = "",
    val productId: String = "",
    val productPrice: String = "",
    val productDes: String = "",
    val productRating: String = "2.5",
    val productDisCount: String = "",
    val productBrand: String = "",
    val productImage: String = "",
    val productCategory: String = "",
    var productQuantity: String = "1",

) : Parcelable


/*
//data class Product (
//
//
//        val productId: Int,
//        val productName: String,
//        val productDescription: String,
//        val brand: String,
//        val price: Double,
//        val salePrice: Double?,
//        val quantityInStock: Int,
//        val stockStatus: String, // "In Stock", "Out of Stock", etc.
//        val productImages: List<String>,
//        val size: String?,
//        val color: String?,
//        val productCategory: String,
//        val productTags: List<String>,
//        val weight: Double,
//        val length: Double,
//        val width: Double,
//        val height: Double,
//        val publishedStatus: Boolean,
//        val visibility: String, // "Public", "Private", etc.
//        val customerReviews: List<String>, // Assuming review content as strings
//        val averageRating: Double,
//        val relatedProducts: List<String>,
//        val discountPercentage: Double?,
//        val discountAmount: Double?
//    )
*/
