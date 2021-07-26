package com.example.muzee.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.Datasource
import com.example.muzee.data.Product
import kotlinx.coroutines.launch

class ProductOverviewViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    init {
        getProducts()
    }

    private fun getProducts() {

        viewModelScope.launch {
            _products.value = Datasource().loadProduct()
        }
    }
}