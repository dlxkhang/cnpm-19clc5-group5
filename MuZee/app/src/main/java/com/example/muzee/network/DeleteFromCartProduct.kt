package com.example.muzee.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeleteFromCartProduct(
    val productId: String?, val NID: String?
) : Parcelable
