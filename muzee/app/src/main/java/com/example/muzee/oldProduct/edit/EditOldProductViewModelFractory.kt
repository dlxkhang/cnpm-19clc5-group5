package com.example.muzee.oldProduct.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.muzee.data.oldProduct

class EditOldProductViewModelFractory(private val oldproduct: oldProduct,
                                              private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditOldProductViewMoldel::class.java)) {
            return EditOldProductViewMoldel(oldproduct, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}