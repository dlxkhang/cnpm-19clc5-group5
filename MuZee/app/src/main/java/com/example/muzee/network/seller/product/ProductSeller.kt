package com.example.muzee.network.seller.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProductSeller(var SID: String,
                    var imageURI: String?,
                    var productCategory: String,
                    var productDescription: String?,
                    var productId: String?,
                    var productName: String,
                    var productPrice: Double,
                    var stock: Int): Parcelable