package com.example.muzee.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.login.Login_input
import com.example.muzee.network.login.Login_response
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    private val _response = MutableLiveData<Login_response?>()
    val response:LiveData<Login_response?> = _response
    fun checkLogin(username: String, password: String) {
        viewModelScope.launch{
            try {
                val login_input = Login_input(username,password)
                val res = repository.checklogin(login_input)
                _response.postValue(res)
            }catch (e:Exception){
                throw RuntimeException("LOGIN POST ERROR",e)
            }
        }
    }
}