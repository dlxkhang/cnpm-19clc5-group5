package com.example.muzee.seller.product_overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.seller.product.ID_Request
import com.example.muzee.network.seller.product.ProductSeller
import kotlinx.coroutines.launch
import java.lang.Exception

class SellerProductOverviewViewModel : ViewModel() {
    enum class ApiStatus { LOADING, ERROR, DONE }
    enum class ApiStatus_Func {SUCCESS,EXIST,ERROR}
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    private val _products = MutableLiveData<List<ProductSeller>>()
    val products: LiveData<List<ProductSeller>> = _products

    private val _status_add = MutableLiveData<ApiStatus_Func>()

    // The external immutable LiveData for the request status
    val status_add: LiveData<ApiStatus_Func> = _status_add

    private val _navigateToSelectedProduct = MutableLiveData<ProductSeller>()
    val navigateToSelectedProduct: LiveData<ProductSeller>
        get() = _navigateToSelectedProduct

    fun getProducts(SID:String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            val idrequest = ID_Request(SID)
            try {
                val response = NetworkLayer.SellerProductApiClient.getSellerProduct(idrequest)
                if(response.isSuccessful){
                    _products.value = response.body()!!
                    _status.value = ApiStatus.DONE
                }

            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun displayProductDetail(product: ProductSeller) {
        _navigateToSelectedProduct.value = product
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
    fun addProduct(product: ProductSeller){
        viewModelScope.launch {
            try {
                val response = NetworkLayer.SellerProductApiClient.addSellerProduct(product)
                if(response.isSuccessful){
                    val data = response.body()!!
                    when(data.ack){
                        "product_exist"->{
                            _status_add.value = ApiStatus_Func.EXIST
                        }
                        "add_product_success"->{
                            _status_add.value = ApiStatus_Func.SUCCESS
                        }
                    }
                }
            }catch (e:Exception){
                _status_add.value = ApiStatus_Func.ERROR
            }
        }
    }
    fun editProduct(product: ProductSeller){
        viewModelScope.launch {
            try {
                val response = NetworkLayer.SellerProductApiClient.editSellerProduct(product)
                if(response.isSuccessful){
                    val data = response.body()!!
                    when(data.ack){
                        "product_not_exist"->{

                        }
                        "edit_product_success"->{

                        }
                    }
                    _status.value = ApiStatus.DONE
                }
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
    fun deleteProduct(productId:String){
        viewModelScope.launch {
            val input = ID_Request(productId)
            try {
                val response = NetworkLayer.SellerProductApiClient.deleteSellerProduct(productId)
                if(response.isSuccessful){
                    val data = response.body()!!
                    when(data.ack){
                        "product_not_exist"->{

                        }
                        "delete_product_success"->{

                        }
                    }
                    _status.value = ApiStatus.DONE
                }
            }catch (e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
}