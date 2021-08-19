package com.example.muzee.data

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Account(
    private var phoneNumber:String): Parcelable
@Parcelize
data class NormalUser(
                  @Json(name="fullname")
                   var fullname :String,
                  @Json(name="phoneNumber")
                   var phoneNumber:String):Account(phoneNumber)
@Parcelize
data class Seller(
    @Json(name="storeName")
              var storeName:String,
    @Json(name="storeAddress")
              var storeAddress:String,
    @Json(name="phoneNumber")
              var phoneNumber:String):Account(phoneNumber)