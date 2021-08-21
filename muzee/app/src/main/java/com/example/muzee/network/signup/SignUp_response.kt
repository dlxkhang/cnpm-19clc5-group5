package com.example.muzee.network.signup

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUp_response(
    @Json(name = "ack")
    val ack:String): Parcelable
