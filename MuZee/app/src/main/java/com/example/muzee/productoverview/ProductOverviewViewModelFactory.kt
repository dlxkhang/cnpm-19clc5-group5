package com.example.muzee.productoverview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.NormalUser

class ProductOverviewViewModelFactory(
    private val NID: String?,
    private val normalUser: NormalUser,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductOverviewViewModel::class.java)) {
            return ProductOverviewViewModel(NID, normalUser, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}