package com.example.muzee.productdetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.Product
import com.example.muzee.network.NewProduct

class ProductDetailViewModelFactory(
    private val product: NewProduct,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(product, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}