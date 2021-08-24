package com.example.muzee.oldProduct

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OldProductViewModelFactory(
    private val NID: String?,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OldProductViewModel::class.java)) {
            return OldProductViewModel(NID, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}