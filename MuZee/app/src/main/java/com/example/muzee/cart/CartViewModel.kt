package com.example.muzee.cart

import Api
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.NormalUser
import com.example.muzee.network.CartProduct
import com.example.muzee.network.DeleteFromCartProduct
import com.example.muzee.network.NormalUserID
import com.example.muzee.network.PlaceOrder
import com.example.muzee.productoverview.ApiStatus
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class CartViewModel(val NID: String?, val normalUser: NormalUser, app: Application): ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private val _products = MutableLiveData<List<CartProduct>>()
    val products: LiveData<List<CartProduct>> = _products

    private val _normalUser = MutableLiveData<NormalUser>()
    val user: LiveData<NormalUser> = _normalUser

    val subtotal_price = MutableLiveData<Double>(0.0)
    val total_price = MutableLiveData<Double>(0.0)

    init {
        setUser()
    }

    private fun setUser(){
        viewModelScope.launch {
            _normalUser.value = normalUser
        }
    }

    fun getProducts(NID: String?) {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _products.value = listOf()
                val normalUserID = NormalUserID(NID)
                _products.value = Api.retrofitService.getCartProducts(NID)
                calculateTotal()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    private fun calculateTotal() {
        var temp = 0.0
        products.value?.forEach {
            temp += it.productPrice
        }
        subtotal_price.value = temp
        total_price.value = temp + 10.0
    }

    fun deleteProductFromCart(productID: String?){
        viewModelScope.launch {
            try {
                val deleteProduct = DeleteFromCartProduct(productID.toString(), NID.toString())
                val temp = Api.retrofitService.deleteFromCart(deleteProduct)
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun placeOrder(address: String) {
        viewModelScope.launch {
            try {
                val placeOrder = PlaceOrder(null, address, "Pending", NID.toString())
                val temp = Api.retrofitService.placeOrder(placeOrder)
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
        total_price.value = temp
    }
}