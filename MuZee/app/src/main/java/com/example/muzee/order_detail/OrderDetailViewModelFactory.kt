package com.example.muzee.order_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.NormalUserOrder
import com.example.muzee.network.UserOrder

class OrderDetailViewModelFactory(
    private val order: UserOrder,
    private val NID: String?,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) {
            return OrderDetailViewModel(order, NID, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}