package com.example.muzee.login
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.*
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    private val _reponse = MutableLiveData<Login_response?>()
    val response:LiveData<Login_response?> = _reponse
    fun checkLogin(username: String, password: String) {
        viewModelScope.launch{
            try {
                val login_input = Login_input(username,password)
                val res = repository.checklogin(login_input)
                _reponse.postValue(res)
            }catch (e:Exception){
                throw RuntimeException("POST ERROR",e)
            }
        }
    }
}