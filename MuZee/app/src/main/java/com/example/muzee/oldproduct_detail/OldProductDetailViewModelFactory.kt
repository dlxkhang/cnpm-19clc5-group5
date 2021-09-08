package com.example.muzee.oldproduct_detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.network.OldProduct

class OldProductDetailViewModelFactory(
    private val product: OldProduct,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OldProductDetailViewModel::class.java)) {
            return OldProductDetailViewModel(product, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}