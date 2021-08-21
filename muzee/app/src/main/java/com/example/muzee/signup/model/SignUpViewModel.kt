package com.example.muzee.signup.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.signup.SignUp_nAccount_input
import com.example.muzee.network.signup.SignUp_response
import com.example.muzee.network.signup.SignUp_sAccount_input
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class SignUpViewModel:ViewModel() {
    val repository = SignUpRepository()
    private val _response = MutableLiveData<SignUp_response?>()
    val reponse: LiveData<SignUp_response?> = _response
    fun addNormalUserAccount(fullName:String,username:String,phoneNumber:String,password:String){
        viewModelScope.launch {
            val signup_input = SignUp_nAccount_input(null,fullName,username,phoneNumber,password)
            val res = repository.addNormalAccount(signup_input)
            _response.value = res
        }
    }
    fun addSellerAccount(storeName:String,addressStore:String,phoneNumber:String,username:String,password:String){
        viewModelScope.launch {
            val signup_input = SignUp_sAccount_input(null,storeName,addressStore,username,phoneNumber,password)
            val res = repository.addSellerAccount(signup_input)
            _response.value = res
        }
    }
}