package com.example.muzee.order_overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.NormalUser

class OrderOverviewViewModelFactory(
    private val NID: String?,
    private val normalUser: NormalUser,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderOverviewViewModel::class.java)) {
            return OrderOverviewViewModel(NID, normalUser, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}