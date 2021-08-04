package com.example.muzee.productdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.muzee.R
import com.example.muzee.data.Product

class ProductDetailViewModel(product: Product, app: Application) : AndroidViewModel(app) {
    private val _selectedProduct = MutableLiveData<Product>()

    // The extenal LiveData for the SelectedProduct
    val selectedProduct: LiveData<Product>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }

    val displayPropertyPrice = Transformations.map(selectedProduct) {
        app.applicationContext.getString(R.string.display_price, it.productPrice)
    }
}