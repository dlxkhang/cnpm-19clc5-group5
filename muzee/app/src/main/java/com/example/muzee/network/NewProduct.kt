package com.example.muzee.network

data class NewProduct (
    val productId: String, val productCategory: String, val productName: String,
    val productPrice: Double, val sellerName: String, val imageUrl: String?,
    val productDescription: String?, val stock: Int
)