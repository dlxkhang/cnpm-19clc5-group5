package com.example.muzee.seller.edit_new_product

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.data.newProduct

class EditNewProductViewMoldel(newproduct: newProduct, app: Application) : AndroidViewModel(app) {
    private val _selectedNewProduct = MutableLiveData<newProduct>()

    // The extenal LiveData for the SelectedProduct
    val selectedNewProduct: LiveData<newProduct>
        get() = _selectedNewProduct

    init {
        _selectedNewProduct.value = newproduct
    }
    @SuppressLint("StringFormatInvalid")
    val displayOldProduct = Transformations.map(selectedNewProduct) {
        app.applicationContext.getString(R.string.new_product_label,it.product_id)
    }
}