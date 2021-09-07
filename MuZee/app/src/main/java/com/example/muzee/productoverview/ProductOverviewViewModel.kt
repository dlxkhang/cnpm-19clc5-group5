package com.example.muzee.productoverview

import Api
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.NormalUser
import com.example.muzee.network.AddToCartProduct
import com.example.muzee.network.NewProduct
import com.example.muzee.network.OldProduct
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class ProductOverviewViewModel (val NID: String?, val normalUser: NormalUser ,app: Application) : ViewModel() {
    private val _isNewProduct = MutableLiveData<Boolean>()
    val isNewProduct:LiveData<Boolean> = _isNewProduct
    var isSearchedCategory = false
    
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private val _products = MutableLiveData<List<NewProduct>>()
    val products: LiveData<List<NewProduct>> = _products

    private val _navigateToSelectedProduct = MutableLiveData<NewProduct>()
    val navigateToSelectedProduct: LiveData<NewProduct>
        get() = _navigateToSelectedProduct
    private var _oldproducts = MutableLiveData<List<OldProduct>>()
    val oldproducts: LiveData<List<OldProduct>> = _oldproducts
    init {
        getNewProducts()
    }

    fun getNewProducts() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _products.value = Api.retrofitService.getNewProducts()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _products.value = listOf()
            }
        }
    }
    fun switchMode(){
        if(_isNewProduct.value == true){
            _isNewProduct.value = false
        }
        else{
            _isNewProduct.value = true
        }
    }
    fun getOldProduct(){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _oldproducts.value = Api.retrofitService.getAllOldProduct()
                _status.value = ApiStatus.DONE
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
                _oldproducts.value = listOf()

            }
        }
    }

    fun searchProduct(key: String?) {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _products.value = Api.retrofitService.searchNewProducts(key)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _products.value = listOf()
            }
        }
    }

    fun addProductToCart(item: NewProduct) {
        viewModelScope.launch {


            try {
                val cartProduct = AddToCartProduct(NID, item.SID, item.productId)
                val temp = Api.retrofitService.addToCart(cartProduct)
            } catch (e: Exception) {

            }
        }
    }

    fun searchProductByCategory(category: String?) {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _products.value = Api.retrofitService.searchNewProductsByCategory(category)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _products.value = listOf()
            }
        }
    }

    fun displayProductDetail(product: NewProduct) {
        _navigateToSelectedProduct.value = product
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
}