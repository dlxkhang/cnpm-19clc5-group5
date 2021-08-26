package com.example.muzee.cart

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.NormalUser

class CartViewModelFactory(
    private val NID: String?,
    private val normalUser: NormalUser,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(NID, normalUser, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}