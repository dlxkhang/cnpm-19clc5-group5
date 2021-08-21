package com.example.muzee.seller.edit_new_product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.newProduct

class EditNewProductViewModelFractory(private val newproduct: newProduct,
                                      private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNewProductViewMoldel::class.java)) {
            return EditNewProductViewMoldel(newproduct, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}