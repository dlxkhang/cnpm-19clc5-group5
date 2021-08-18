package com.example.muzee.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login_input(
    @Json(name="username")
    val usr:String,
    @Json(name="password")
    val pwd:String): Parcelable
