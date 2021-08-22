package com.example.muzee.network.seller.order.get_list_order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderedProduct(
    val imageURI: String?,
    val productCategory: String,
    val productDescription: String?,
    val productId: String,
    val productName: String,
    val productPrice: Double,
    val sellerName: String,
    val stock: Int
): Parcelable