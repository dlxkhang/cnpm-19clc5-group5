package com.example.muzee.cart

import Api
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.CartProduct
import com.example.muzee.network.DeleteFromCartProduct
import com.example.muzee.network.NormalUserID
import com.example.muzee.productoverview.ApiStatus
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class CartViewModel(val NID: String?, app: Application): ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private val _products = MutableLiveData<List<CartProduct>>()
    val products: LiveData<List<CartProduct>> = _products

    val total_price = MutableLiveData<Double>(0.0)

    init {
        getProducts(NID)
    }

    private fun getProducts(NID: String?) {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val normalUserID = NormalUserID(NID)
                _products.value = Api.retrofitService.getCartProducts(NID)
                calculateTotal()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _products.value = listOf()
            }
        }
    }

    private fun calculateTotal() {
        var temp = 0.0
        products.value?.forEach {
            temp += it.productPrice
        }
        total_price.value = temp
    }

    fun deleteProductFromCart(productID: String?){
        viewModelScope.launch {
            try {
                val deleteProduct = DeleteFromCartProduct(productID, NID)
                Api.retrofitService.deleteFromCart(deleteProduct)
                _products.value = Api.retrofitService.getCartProducts(NID)
                calculateTotal()
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _products.value = listOf()
            }
        }
    }
}