package com.example.muzee.seller.product_overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.Datasource
import com.example.muzee.data.Product
import com.example.muzee.data.newProduct
import kotlinx.coroutines.launch

class SellerProductOverviewViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _navigateToSelectedProduct = MutableLiveData<Product>()
    val navigateToSelectedProduct: LiveData<Product>
        get() = _navigateToSelectedProduct

    init {
        getProducts()
    }

    private fun getProducts() {

        viewModelScope.launch {
            _products.value = Datasource().loadProduct()
        }
    }

    fun displayProductDetail(product: Product) {
        _navigateToSelectedProduct.value = product
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
    fun addAnNewProduct(newProduct: newProduct){

    }
}