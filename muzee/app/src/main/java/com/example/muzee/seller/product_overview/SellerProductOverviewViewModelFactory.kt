package com.example.muzee.seller.product_overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SellerProductOverviewViewModelFactory(
    private val sellerID :String,
    private val application:Application
):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SellerProductOverviewViewModel::class.java)){
            return SellerProductOverviewViewModel(sellerID,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}