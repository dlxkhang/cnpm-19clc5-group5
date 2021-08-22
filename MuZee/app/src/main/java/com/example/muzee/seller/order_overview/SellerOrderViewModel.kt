package com.example.muzee.seller.order_overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.Datasource
import com.example.muzee.data.Order
import com.example.muzee.data.SellerOrder
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.seller.order.OrderApiClient
import com.example.muzee.network.seller.order.Order_request
import com.example.muzee.network.seller.order.Order_responseItem
import com.example.muzee.productoverview.ApiStatus
import kotlinx.coroutines.launch
import java.lang.Exception

class SellerOrderViewModel : ViewModel() {
    enum class ApiStatus { LOADING, ERROR, DONE }
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    private val _listOfOrders = MutableLiveData<List<Order_responseItem>>()
    val listOfOrders: LiveData<List<Order_responseItem>> = _listOfOrders

    private val _navigateToSelectedOrder = MutableLiveData<Order_responseItem>()
    val navigateToSelectedOrder: LiveData<Order_responseItem>
        get() = _navigateToSelectedOrder


    fun getListOfOrders(SID:String) {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val order_req = Order_request(SID)
            try{
                val response = NetworkLayer.OrderApiClient.getOrderSeller(order_req)
                if(response.isSuccessful) {
                    _status.value = ApiStatus.DONE
                    val data = response.body()
                    _listOfOrders.value = data
                }
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
                _listOfOrders.value = listOf()
            }
        }
    }

    fun displayOrderDetail(order: Order_responseItem) {
        _navigateToSelectedOrder.value = order
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedOrder.value = null
    }
}