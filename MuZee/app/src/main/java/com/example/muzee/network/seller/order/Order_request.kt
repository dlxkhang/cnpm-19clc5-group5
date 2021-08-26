package com.example.muzee.network.seller.order

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order_request( @Json(name = "accountID")
                          val accountID: String): Parcelable
