package com.example.muzee.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Product(
    var productCategory: Category,
    var productName:String,
    var productPrice:Double,
    var sellerName:String,
    var imageURI: String? = null,
    var productDescription:String? = null): Parcelable


class oldProduct(
    product_category: Category,
    product_name:String,
    product_price:Double,
    seller_name:String,
    val condidtion:String?
):Product(product_category,product_name,product_price,seller_name){
    constructor(parcel: Parcel) : this(
        TODO("productCategory"),
        TODO("productName"),
        TODO("productPrice"),
        TODO("sellerName"),
        parcel.readString()){

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(condidtion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<oldProduct> {
        override fun createFromParcel(parcel: Parcel): oldProduct {
            return oldProduct(parcel)
        }

        override fun newArray(size: Int): Array<oldProduct?> {
            return arrayOfNulls(size)
        }
    }

}

class newProduct(
    product_category: Category,

    product_name:String,

    product_price:Double,

    seller_name:String,
    val condidtion:String?
):Product(product_category,product_name,product_price,seller_name){
    constructor(parcel: Parcel) : this(
        TODO("productCategory"),
        TODO("productName"),
        TODO("productPrice"),
        TODO("sellerName"),
        parcel.readString()){

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(condidtion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<oldProduct> {
        override fun createFromParcel(parcel: Parcel): oldProduct {
            return oldProduct(parcel)
        }

        override fun newArray(size: Int): Array<oldProduct?> {
            return arrayOfNulls(size)
        }
    }

}

enum class Category(var category: String){
    Piano("Piano"),
    Organ("Organ"),
    Drum("Drum"),
    Guitar("Guitar Bass"),
    Electronic("Guitar Electronic"),
    Bass("Guitar Bass")
}

