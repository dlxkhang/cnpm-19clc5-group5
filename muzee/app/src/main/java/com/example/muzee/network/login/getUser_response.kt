package com.example.muzee.network.login

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class getUser_response(
    @Json(name = "accountID")
    val accountID: String
): Parcelable
