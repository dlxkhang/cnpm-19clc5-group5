package com.example.muzee.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AckResponse(
    @field:Json(name = "ack")
    val ack: String
    ): Parcelable
