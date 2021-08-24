package com.example.muzee.seller.edit_new_product

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.data.newProduct
import com.example.muzee.network.seller.product.ProductSeller

class EditNewProductViewMoldel(newproduct: ProductSeller, app: Application) : AndroidViewModel(app) {
    private val _selectedNewProduct = MutableLiveData<ProductSeller>()

    // The extenal LiveData for the SelectedProduct
    val selectedNewProduct: LiveData<ProductSeller>
        get() = _selectedNewProduct

    init {
        _selectedNewProduct.value = newproduct
    }
    @SuppressLint("StringFormatInvalid")
    val displayOldProduct = Transformations.map(selectedNewProduct) {
        app.applicationContext.getString(R.string.new_product_label,it.productId)
    }
}