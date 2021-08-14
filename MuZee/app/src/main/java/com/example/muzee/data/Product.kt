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
    var condition:Int?
):Product(product_category,product_name,product_price,seller_name) {
    constructor(parcel: Parcel) : this(
    TODO("product_category"),
    TODO("product_name"),
    TODO("product_price"),
    TODO("seller_name"),
    parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(condition!!)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<newProduct> {
        override fun createFromParcel(parcel: Parcel): newProduct {
            return newProduct(parcel)
        }

        override fun newArray(size: Int): Array<newProduct?> {
            return arrayOfNulls(size)
        }
    }
}
class newProduct(
    product_category: Category,

    product_name:String,

    product_price:Double,

    seller_name:String,
    val stock: Int
):Product(product_category,product_name,product_price,seller_name) {
    constructor(parcel: Parcel) : this(
        TODO("product_category"),
        TODO("product_name"),
        TODO("product_price"),
        TODO("seller_name"),
        parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(stock)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<newProduct> {
        override fun createFromParcel(parcel: Parcel): newProduct {
            return newProduct(parcel)
        }

        override fun newArray(size: Int): Array<newProduct?> {
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

