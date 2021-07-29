package com.example.muzee.seller.order_detail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.muzee.R
import com.example.muzee.data.Order

class SellerOrderDetailViewModel(order: Order, app: Application) : AndroidViewModel(app) {
    private val _selectedOrder = MutableLiveData<Order>()

    // The extenal LiveData for the SelectedProduct
    val selectedOrder: LiveData<Order>
        get() = _selectedOrder

    init {
        _selectedOrder.value = order
    }

    @SuppressLint("StringFormatInvalid")
    val displayOrderDetails = Transformations.map(selectedOrder) {
        app.applicationContext.getString(R.string.order_label,it.orderId)
    }
}