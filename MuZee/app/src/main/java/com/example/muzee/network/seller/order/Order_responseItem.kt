package com.example.muzee.network.seller.order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order_responseItem(
    val customerName: String,
    val deliveryCharges: Int,
    val itemTotal: Double,
    val orderAddress: String,
    val orderId: String,
    val orderStatus: String,
    val orderedProducts: List<OrderedProduct>
): Parcelable