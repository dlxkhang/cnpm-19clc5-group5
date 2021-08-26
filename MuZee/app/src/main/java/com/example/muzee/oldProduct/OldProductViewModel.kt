package com.example.muzee.oldProduct

import Api
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.AddOldProduct
import com.example.muzee.network.OldProduct
import com.example.muzee.network.OldProductID
import kotlinx.coroutines.launch

enum class ApiStatus {SUCCESS,EXIST,ERROR}

class OldProductViewModel(val NID: String?, val app: Application): ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private var _oldproducts = MutableLiveData<List<OldProduct>>()
    val oldproducts: LiveData<List<OldProduct>> = _oldproducts

    private val _navigateToSelectedProduct = MutableLiveData<OldProduct>()
    val navigateToSelectedProduct: LiveData<OldProduct>
        get() = _navigateToSelectedProduct


    fun getOldProducts() {

        viewModelScope.launch {

            try {
                _oldproducts.value = Api.retrofitService.getOldProducts(NID)

            } catch (e: Exception) {
                _oldproducts.value = listOf()
            }
        }
    }

    fun displayOldProductDetail(oldproduct: OldProduct) {
        _navigateToSelectedProduct.value = oldproduct
    }

    fun displayOldProductDetailComplete() {
        _navigateToSelectedProduct.value = null
    }

    fun addAnOldProduct(oldproduct: AddOldProduct){
        viewModelScope.launch {
            try {
                val response = Api.retrofitService.addOldProduct(oldproduct)
                if(response.isSuccessful){
                    val data = response.body()!!
                    when(data.ack){
                        "old_product_exist"->{
                            _status.value = ApiStatus.EXIST
                        }
                        "add_old_product_success"->{
                            _status.value = ApiStatus.SUCCESS
                        }
                    }
                }
            }catch (e: java.lang.Exception){
                _status.value = ApiStatus.ERROR
            }
        }


    }

    fun editAnOldProduct(oldproduct: AddOldProduct){
        viewModelScope.launch {
            val temp = Api.retrofitService.editOldProduct(oldproduct)
        }
    }

    fun deleteAnOldProduct(oldProductID: String?) {
        viewModelScope.launch {
            try {
                val PID = OldProductID(oldProductID)
                val temp = Api.retrofitService.deleteOldProduct(PID)
            } catch (e: Exception) {

            }
        }
    }
}