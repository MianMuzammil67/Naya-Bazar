package com.example.nayabazar.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    var id : String,
    val colour : String?,
    val name: String,
    val brief :String?,
    val icon : String
) : Parcelable {
    constructor() : this("","","","","")

}
