package com.example.muzee.oldProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.Datasource
import com.example.muzee.data.oldProduct
import kotlinx.coroutines.launch

class OldProductViewModel: ViewModel() {
    private var _oldproducts = MutableLiveData<List<oldProduct>>()
    val oldproducts: LiveData<List<oldProduct>> = _oldproducts

    private val _navigateToSelectedProduct = MutableLiveData<oldProduct>()
    val navigateToSelectedProduct: LiveData<oldProduct>
        get() = _navigateToSelectedProduct

    init {
        getOldProducts()
    }

    private fun getOldProducts() {

        viewModelScope.launch {
            _oldproducts.value = Datasource().loadOldProduct()
        }
    }

    fun displayOldProductDetail(oldproduct: oldProduct) {
        _navigateToSelectedProduct.value = oldproduct
    }
    fun addAnOldProduct(oldproduct: oldProduct){
         _oldproducts.value = _oldproducts.value?.plus(oldproduct)
    }
    fun deleteAnProduct(oldproduct:oldProduct){
        _oldproducts.value = _oldproducts.value?.minusElement(oldproduct)
    }
    fun editAnProduct(){

    }
}