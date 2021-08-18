package com.example.muzee.signup.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.NormalUser
import com.example.muzee.network.signup.SignUp_nAccount_input
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class SignUp_NormalUsersViewModel:ViewModel() {
    val repository = SignUpRepository()
    private val _response = MutableLiveData<String?>()
    val reponse:LiveData<String?> = _response
    fun addAnAccount(fullName:String,username:String,phoneNumber:String,password:String){
        viewModelScope.launch {
            try{
                val signup_input = SignUp_nAccount_input(null,fullName,username,phoneNumber,password)
                val res = repository.addNormalAccount(signup_input)
                _response.value = res
            }catch (e:Exception){
                throw RuntimeException("SIGN UP POST ERROR",e)
            }
        }
    }
}