package com.example.muzee.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product (
    val productTitle: String,
    val productPrice: Double
): Parcelable