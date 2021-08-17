package com.example.muzee.cart

import Api
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.CartProduct
import com.example.muzee.productoverview.ApiStatus
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class CartViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private val _products = MutableLiveData<List<CartProduct>>()
    val products: LiveData<List<CartProduct>> = _products

    init {
        getProducts()
    }

    private fun getProducts() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _products.value = Api.retrofitService.getCartProducts()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _products.value = listOf()
            }
        }
    }
}