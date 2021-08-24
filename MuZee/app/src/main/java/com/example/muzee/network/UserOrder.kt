package com.example.muzee.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserOrder(val orderId: String, val orderAddress: String, val orderedProducts: List<NewProduct>,
val itemTotal: Double, val deliveryCharges: Double, val orderStatus: String, val customerName: String): Parcelable {
    fun totalPrice(): Double {
        return itemTotal + deliveryCharges
    }
}
