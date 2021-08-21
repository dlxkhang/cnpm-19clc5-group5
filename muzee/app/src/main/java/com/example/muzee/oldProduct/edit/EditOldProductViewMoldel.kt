package com.example.muzee.oldProduct.edit

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.data.oldProduct

class EditOldProductViewMoldel(oldproduct: oldProduct, app: Application) : AndroidViewModel(app) {
    private val _selectedOldProduct = MutableLiveData<oldProduct>()

    // The extenal LiveData for the SelectedProduct
    val selectedOldProduct: LiveData<oldProduct>
        get() = _selectedOldProduct

    init {
        _selectedOldProduct.value = oldproduct
    }
    @SuppressLint("StringFormatInvalid")
    val displayOldProduct = Transformations.map(selectedOldProduct) {
        app.applicationContext.getString(R.string.old_product_label,it.product_id)
    }
}