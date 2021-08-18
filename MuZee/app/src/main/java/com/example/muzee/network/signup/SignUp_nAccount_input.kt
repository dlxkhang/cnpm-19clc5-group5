package com.example.muzee.network.signup

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUp_nAccount_input(
    @Json(name="accountId")
    private var id: String?,
    @Json(name = "fullname")
    private var fullName :String,
    @Json(name="username")
    private var username:String,
    @Json(name="phoneNumber")
    private var phoneNumber:String,
    @Json(name="password")
    private var password:String
): Parcelable
