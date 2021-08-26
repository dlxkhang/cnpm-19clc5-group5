package com.example.muzee.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OldProduct(
    val productId: String?, val productCategory: String, val productName: String,
    val sellerName: String, val NID: String, val imageURI: String?,
    val productDescription: String?, val condition: Int
): Parcelable

@Parcelize
data class AddOldProduct(
    val productId: String?, val productCategory: String, val productName: String,
    val NID: String?, val imageURI: String?,
    val productDescription: String?, val condition: Int
): Parcelable

@Parcelize
data class OldProductID(
    val productId: String?
): Parcelable