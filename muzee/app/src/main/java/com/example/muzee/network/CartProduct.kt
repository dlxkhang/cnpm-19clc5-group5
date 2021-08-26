package com.example.muzee.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartProduct (
    val productName: String, val productPrice: Double
    ) : Parcelable