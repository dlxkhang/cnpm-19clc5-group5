package com.example.muzee.seller.product_detail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.muzee.R
import com.example.muzee.network.seller.product.ProductSeller

class SellerProductDetailViewModel(product: ProductSeller, app: Application) : AndroidViewModel(app) {
    private val _selectedProduct = MutableLiveData<ProductSeller>()

    // The extenal LiveData for the SelectedProduct
    val selectedProduct: LiveData<ProductSeller>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }

    @SuppressLint("StringFormatInvalid")
    val displayPropertyPrice = Transformations.map(selectedProduct) {
        app.applicationContext.getString(R.string.display_price, it.productPrice)
    }
}