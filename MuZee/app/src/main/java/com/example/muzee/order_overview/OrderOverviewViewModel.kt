package com.example.muzee.order_overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.Datasource
import com.example.muzee.data.NormalUserOrder
import kotlinx.coroutines.launch

class OrderOverviewViewModel : ViewModel() {

    private val _listOfOrders = MutableLiveData<List<NormalUserOrder>>()
    val listOfOrders: LiveData<List<NormalUserOrder>> = _listOfOrders

    private val _navigateToSelectedOrder = MutableLiveData<NormalUserOrder>()
    val navigateToSelectedOrder: LiveData<NormalUserOrder>
        get() = _navigateToSelectedOrder

    init {
        getListOfOrders()
    }

    private fun getListOfOrders() {

        viewModelScope.launch {
            _listOfOrders.value = Datasource().loadOrder()
        }
    }

    fun displayOrderDetail(order: NormalUserOrder) {
        _navigateToSelectedOrder.value = order
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedOrder.value = null
    }
}