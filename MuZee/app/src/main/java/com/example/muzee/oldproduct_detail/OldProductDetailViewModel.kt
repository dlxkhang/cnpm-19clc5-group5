package com.example.muzee.oldproduct_detail

import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.network.OldProduct

class OldProductDetailViewModel(oldProduct:OldProduct,app:Application):AndroidViewModel(app) {
    private val _seletectOldProduct = MutableLiveData<OldProduct>()
    val selectedOldProduct:LiveData<OldProduct> = _seletectOldProduct
    init {
        _seletectOldProduct.value = oldProduct
    }
    val displayPropertyCondition = Transformations.map(selectedOldProduct){
        app.applicationContext.getString(R.string.condition,it.condition.toString())
    }
}