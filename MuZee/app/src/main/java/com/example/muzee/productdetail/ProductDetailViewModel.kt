package com.example.muzee.productdetail

import Api
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.network.AddToCartProduct
import com.example.muzee.network.NewProduct
import kotlinx.coroutines.launch

class ProductDetailViewModel(product: NewProduct, app: Application) : AndroidViewModel(app) {
    private val _selectedProduct = MutableLiveData<NewProduct>()

    // The extenal LiveData for the SelectedProduct
    val selectedProduct: LiveData<NewProduct>
        get() = _selectedProduct

    init {
        _selectedProduct.value = product
    }

    val displayPropertyPrice = Transformations.map(selectedProduct) {
        app.applicationContext.getString(R.string.display_price, it.productPrice)
    }

    fun addProductToCart() {
        viewModelScope.launch {
            val cartProduct = AddToCartProduct("001",selectedProduct.value!!.SID, selectedProduct.value!!.productId)
            Api.retrofitService.addToCart(cartProduct)
        }
    }
}