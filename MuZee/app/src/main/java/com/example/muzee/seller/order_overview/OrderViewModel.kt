package com.example.muzee.seller.order_overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.Datasource
import com.example.muzee.data.Order
import com.example.muzee.data.SellerOrder
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val _listOfOrders = MutableLiveData<List<SellerOrder>>()
    val listOfOrders: LiveData<List<SellerOrder>> = _listOfOrders

    private val _navigateToSelectedOrder = MutableLiveData<SellerOrder>()
    val navigateToSelectedOrder: LiveData<SellerOrder>
        get() = _navigateToSelectedOrder

    init {
        getListOfOrders()
    }

    private fun getListOfOrders() {

        viewModelScope.launch {
            _listOfOrders.value = Datasource().loadOrder()
        }
    }

    fun displayOrderDetail(order: SellerOrder) {
        _navigateToSelectedOrder.value = order
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedOrder.value = null
    }
}