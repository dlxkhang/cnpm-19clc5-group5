package com.example.muzee.network.seller.product

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ID_Request(@Json(name = "SID")
                      val accountID: String): Parcelable
