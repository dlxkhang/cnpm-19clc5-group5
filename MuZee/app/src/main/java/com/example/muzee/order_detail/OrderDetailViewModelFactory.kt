package com.example.muzee.order_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.NormalUserOrder

class OrderDetailViewModelFactory(
    private val order: NormalUserOrder,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) {
            return OrderDetailViewModel(order, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}