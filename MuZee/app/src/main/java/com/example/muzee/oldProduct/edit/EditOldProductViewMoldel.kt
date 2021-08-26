package com.example.muzee.oldProduct.edit

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.muzee.R
import com.example.muzee.network.OldProduct

class EditOldProductViewMoldel(oldproduct: OldProduct, app: Application) : AndroidViewModel(app) {
    private val _selectedOldProduct = MutableLiveData<OldProduct>()

    // The extenal LiveData for the SelectedProduct
    val selectedOldProduct: LiveData<OldProduct>
        get() = _selectedOldProduct

    init {
        _selectedOldProduct.value = oldproduct
    }
    @SuppressLint("StringFormatInvalid")
    val displayOldProduct = Transformations.map(selectedOldProduct) {
        app.applicationContext.getString(R.string.old_product_label,it.productId)
    }
}