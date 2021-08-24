package com.example.muzee.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceOrder (
    val orderId: String?, val orderAddress: String, val orderStatus: String, val NID: String
) : Parcelable
