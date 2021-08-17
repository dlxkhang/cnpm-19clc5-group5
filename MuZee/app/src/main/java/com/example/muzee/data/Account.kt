package com.example.muzee.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Account(
    private var id: String,
    private var phoneNumber:String,
    private var username:String,
    private var password:String): Parcelable
class NormalUser( private var id: String,
                  private var fullName :String,
                  private var phoneNumber:String,
                  private var username:String,
                  private var password:String):Account(id,phoneNumber,username,password) {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(id)
        parcel.writeString(fullName)
        parcel.writeString(phoneNumber)
        parcel.writeString(username)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NormalUser> {
        override fun createFromParcel(parcel: Parcel): NormalUser {
            return NormalUser(parcel)
        }

        override fun newArray(size: Int): Array<NormalUser?> {
            return arrayOfNulls(size)
        }
    }
}

class Seller(private var id: String,
             private var storeName:String,
             private var storeAddress:String,
             private var phoneNumber:String,
             private var username:String,
             private var password:String):Account(id,phoneNumber,username,password) {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(id)
        parcel.writeString(storeName)
        parcel.writeString(storeAddress)
        parcel.writeString(phoneNumber)
        parcel.writeString(username)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Seller> {
        override fun createFromParcel(parcel: Parcel): Seller {
            return Seller(parcel)
        }

        override fun newArray(size: Int): Array<Seller?> {
            return arrayOfNulls(size)
        }
    }
}