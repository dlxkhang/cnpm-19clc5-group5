package com.example.muzee.seller.order_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.network.seller.order.Order_responseItem

class SellerOrderDetailViewModelFactory(
    private val order: Order_responseItem,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SellerOrderDetailViewModel::class.java)) {
            return SellerOrderDetailViewModel(order, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}