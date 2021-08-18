package com.example.muzee.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login_response(
    @Json(name="ack")
    val ack: String,
    @Json(name="ID")
    val ID:String? = null): Parcelable