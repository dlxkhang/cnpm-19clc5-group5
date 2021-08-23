package com.example.muzee.network.seller.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductSeller(val SID: String,
                    val imageURI: String?,
                    val productCategory: String,
                    val productDescription: String?,
                    val productId: String?,
                    val productName: String,
                    val productPrice: Double,
                    val stock: Int): Parcelable