package com.example.muzee.oldProduct.add

import Api
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.AddOldProduct
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class AddOldProductViewModel: ViewModel() {
    enum class ApiStatus {SUCCESS,EXIST,ERROR}
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

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
                        "add_product_success"->{
                            _status.value = ApiStatus.SUCCESS
                        }
                    }
                }
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
                throw IllegalStateException(e.message)
            }
        }
    }
}