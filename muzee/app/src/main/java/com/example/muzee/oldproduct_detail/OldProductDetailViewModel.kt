package com.example.muzee.oldproduct_detail

import android.app.Application
import androidx.lifecycle.*
import com.example.muzee.R
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.OldProduct
import com.example.muzee.network.login.getUser_response
import kotlinx.coroutines.launch
import java.lang.Exception

class OldProductDetailViewModel(oldProduct:OldProduct,app:Application):AndroidViewModel(app) {
    private val _seletectOldProduct = MutableLiveData<OldProduct>()
    val selectedOldProduct:LiveData<OldProduct> = _seletectOldProduct
    init {
        _seletectOldProduct.value = oldProduct
        getnumberphoneofNU()
    }
    val displayPropertyCondition = Transformations.map(selectedOldProduct){
        app.applicationContext.getString(R.string.condition,it.condition.toString())
    }
    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber:LiveData<String> = _phoneNumber
    fun getnumberphoneofNU(){
        val _input = getUser_response(_seletectOldProduct.value!!.NID)
        viewModelScope.launch {
            try{
                val response = NetworkLayer.loginRetrofitService.getNormalUserInfo(_input)
                if(response.isSuccessful){
                    _phoneNumber.value = response.body()!!.phoneNumber
                }
            }catch (e:Exception){

            }
        }

    }
}