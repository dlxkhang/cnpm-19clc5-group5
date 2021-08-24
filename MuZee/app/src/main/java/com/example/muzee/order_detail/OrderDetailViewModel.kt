package com.example.muzee.order_detail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.muzee.R
import com.example.muzee.network.UserOrder

class OrderDetailViewModel(order: UserOrder, NID: String?, app: Application) : AndroidViewModel(app) {
    val _selectedOrder = MutableLiveData<UserOrder>()

    // The extenal LiveData for the SelectedProduct
    val selectedOrder: LiveData<UserOrder>
        get() = _selectedOrder

    @SuppressLint("StringFormatInvalid")
    val displayOrderDetails = Transformations.map(selectedOrder) {
        app.applicationContext.getString(R.string.order_label,it.orderId)
    }
}