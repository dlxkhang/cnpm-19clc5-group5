package com.example.muzee.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.network.LoginApi
import kotlinx.coroutines.launch
import java.io.UnsupportedEncodingException

class LoginViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        initAccount()
    }

    private fun initAccount() {
        viewModelScope.launch {
            try {
                val account = LoginApi.loginRetrofitService.getAccount()
                _status.value = "Success: Account received"
            } catch (e: Exception) {
                _status.value = "Failure:${e.message}"
            }
        }
    }
    // JSON:
    // [
    // {
    //      username:String,
    //      password:String,
    // }
    // ]
    private fun createAuthToken(username: String,password: String): String {
        var data = byteArrayOf(0)
        try {
            data = (username+":"+password)?.toByteArray(Charsets.UTF_8)
        } catch (e: UnsupportedEncodingException){
            e.printStackTrace()
        }
        return "Basic "
    }
    fun checkLogin(username: String, password: String) {
        val autokenString :String = createAuthToken(username,password)
        LoginApi.loginRetrofitService.checklogin(autokenString)
    }
}