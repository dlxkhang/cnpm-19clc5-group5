package com.example.muzee.network.seller.order.get_list_order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order_responseItem(
    val customerName: String,
    val deliveryCharges: Int,
    val itemTotal: Int,
    val orderAddress: String,
    val orderId: String,
    val orderStatus: String,
    val orderedProducts: List<OrderedProduct>
): Parcelable