package com.example.muzee.seller.add_new_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.seller.product.ProductSeller
import kotlinx.coroutines.launch

class AddNewProductFragmentViewModel: ViewModel() {
    enum class ApiStatus {SUCCESS,EXIST,ERROR}
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    fun addProduct(product: ProductSeller){
        viewModelScope.launch {
            try {
                val response = NetworkLayer.SellerProductApiClient.addSellerProduct(product)
                if(response.isSuccessful){
                    val data = response.body()!!
                    when(data.ack){
                        "product_exist"->{
                            _status.value = ApiStatus.EXIST
                        }
                        "add_product_success"->{
                            _status.value = ApiStatus.SUCCESS
                        }
                    }
                }
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
}