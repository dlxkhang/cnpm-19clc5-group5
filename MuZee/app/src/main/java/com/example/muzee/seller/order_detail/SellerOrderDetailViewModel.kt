package com.example.muzee.seller.order_detail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.muzee.R
import com.example.muzee.network.seller.order.get_list_order.Order_responseItem

class SellerOrderDetailViewModel(order: Order_responseItem, app: Application) : AndroidViewModel(app) {
    private val _selectedOrder = MutableLiveData<Order_responseItem>()

    // The extenal LiveData for the SelectedProduct
    val selectedOrder: LiveData<Order_responseItem>
        get() = _selectedOrder

    init {
        _selectedOrder.value = order
    }

    @SuppressLint("StringFormatInvalid")
    val displayOrderDetails = Transformations.map(selectedOrder) {
        app.applicationContext.getString(R.string.order_label,it.orderId)
    }
}