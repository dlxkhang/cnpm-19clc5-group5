package com.example.muzee.oldproduct_overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.OldProduct
import com.example.muzee.productoverview.ApiStatus
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
class OldProductOverviewViewModel:ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    private var _oldproducts = MutableLiveData<List<OldProduct>>()
    val oldproducts: LiveData<List<OldProduct>> = _oldproducts
    private val _navigateToSelectedOldProduct = MutableLiveData<OldProduct>()
    val navigateToSelectedOldProduct: LiveData<OldProduct> = _navigateToSelectedOldProduct
    init {
        getOldProduct()
    }
    fun getOldProduct(){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _oldproducts.value = Api.retrofitService.getAllOldProduct()
                _status.value = ApiStatus.DONE
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
                _oldproducts.value = listOf()

            }
        }
    }
    fun displayOldProductDetail(product:OldProduct){
        _navigateToSelectedOldProduct.value = product
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedOldProduct.value = null
    }

}