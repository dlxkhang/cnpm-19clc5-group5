package com.example.muzee.oldProduct

import Api
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.AddOldProduct
import com.example.muzee.network.OldProduct
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class OldProductViewModel: ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private var _oldproducts = MutableLiveData<List<OldProduct>>()
    val oldproducts: LiveData<List<OldProduct>> = _oldproducts

    private val _navigateToSelectedProduct = MutableLiveData<OldProduct>()
    val navigateToSelectedProduct: LiveData<OldProduct>
        get() = _navigateToSelectedProduct

    init {
        getOldProducts()
    }

    private fun getOldProducts() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _oldproducts.value = Api.retrofitService.getOldProducts()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _oldproducts.value = listOf()
            }
        }
    }

    fun displayOldProductDetail(oldproduct: OldProduct) {
        _navigateToSelectedProduct.value = oldproduct
    }
    fun addAnOldProduct(oldproduct: AddOldProduct){
        viewModelScope.launch {
            Api.retrofitService.addOldProduct(oldproduct)
        }
    }
    fun deleteAnOldProduct(oldProductID: String?) {
        viewModelScope.launch {
            Api.retrofitService.deleteOldProduct(oldProductID)
        }
    }
}