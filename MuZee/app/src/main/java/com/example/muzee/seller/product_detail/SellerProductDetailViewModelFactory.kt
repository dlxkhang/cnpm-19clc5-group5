package com.example.muzee.seller.product_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.Seller
import com.example.muzee.network.seller.product.ProductSeller

class SellerProductDetailViewModelFactory(
    private val seller: Seller,
    private val product: ProductSeller,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SellerProductDetailViewModel::class.java)) {
            return SellerProductDetailViewModel(product,seller, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}