package com.example.muzee.network.seller.order

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FunctionOrderRequest(
    @Json(name = "SID")
    val SID:String,
    @Json(name = "OID")
    val OID:String,
): Parcelable