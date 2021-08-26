package com.example.muzee.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewProduct (
    val productId: String, val productCategory: String, val productName: String,
    val productPrice: Double, val sellerName: String, val SID: String,
    val imageURI: String?, val productDescription: String?, val stock: Int
): Parcelable