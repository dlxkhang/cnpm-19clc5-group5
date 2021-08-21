package com.example.muzee.signup.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.muzee.R
import com.example.muzee.login.LoginViewModel
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.signup.SignUp_nAccount_input
import com.example.muzee.network.signup.SignUp_response
import com.example.muzee.network.signup.SignUp_sAccount_input
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class SignUpViewModel:ViewModel() {
    enum class ApiStatus { ERROR,EXISTACCOUNT,SUCCESS}
    private val _status = MutableLiveData<ApiStatus>()
    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    fun addNormalUserAccount(fullName:String,username:String,phoneNumber:String,password:String){
        viewModelScope.launch {
            val signup_input = SignUp_nAccount_input(null,fullName,username,phoneNumber,password)
            try{
                val request = NetworkLayer.SignUpApiClient.addNormalAccount(signup_input)
                if(request.isSuccessful) {
                    val data = request.body()
                    when (data?.ack) {
                        "account_exist" -> {
                            _status.value = ApiStatus.EXISTACCOUNT
                        }
                        "sign_up_success" -> {
                            _status.value = ApiStatus.SUCCESS
                        }
                    }
                }
            }catch(e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
    fun addSellerAccount(storeName:String,addressStore:String,phoneNumber:String,username:String,password:String){
        viewModelScope.launch {
            val signup_input = SignUp_sAccount_input(null,storeName,addressStore,username,phoneNumber,password)
            try{
                val request = NetworkLayer.SignUpApiClient.addSellerAccount(signup_input)
                if(request.isSuccessful) {
                    val data = request.body()
                    when (data?.ack) {
                        "account_exist" -> {
                            _status.value = ApiStatus.EXISTACCOUNT
                        }
                        "sign_up_success" -> {
                            _status.value = ApiStatus.SUCCESS
                        }
                    }
                }
            }catch(e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
}