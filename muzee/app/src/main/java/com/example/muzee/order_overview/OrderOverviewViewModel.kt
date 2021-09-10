package com.example.muzee.order_overview

import Api
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.NormalUser
import com.example.muzee.network.UserOrder
import com.example.muzee.productoverview.ApiStatus
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class OrderOverviewViewModel(val NID: String?, val normalUser: NormalUser, app: Application) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private val _listOfOrders = MutableLiveData<List<UserOrder>>()
    val listOfOrders: LiveData<List<UserOrder>> = _listOfOrders

    private val _navigateToSelectedOrder = MutableLiveData<UserOrder>()
    val navigateToSelectedOrder: LiveData<UserOrder>
        get() = _navigateToSelectedOrder

    fun getOrders() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _listOfOrders.value = listOf()
                _listOfOrders.value = Api.retrofitService.getUserOders(NID)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun displayOrderDetail(order: UserOrder) {
        _navigateToSelectedOrder.value = order
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedOrder.value = null
    }
}