package com.example.muzee.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.NormalUser
import com.example.muzee.data.Seller
import com.example.muzee.network.login.Login_input
import com.example.muzee.network.login.Login_response
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    private val _userAccount = MutableLiveData<NormalUser>()
    val userAccount:LiveData<NormalUser> = _userAccount
    private val _sellerAccount = MutableLiveData<Seller>()
    val sellerAccount:LiveData<Seller> = _sellerAccount
    private val _response = MutableLiveData<Login_response?>()
    val response:LiveData<Login_response?> = _response


    fun checkLogin(username: String, password: String) {
        viewModelScope.launch{
            val login_input = Login_input(username,password)
            val res = repository.checklogin(login_input)
            _response.value = res
        }
    }
    fun getNormalUserInfo( id:String ){
        viewModelScope.launch {
            val res = repository.getNormalUserInfo(id)
            res?.let {
                _userAccount.value = it
            }

        }
    }
    fun getSellerInfo(id:String){
        viewModelScope.launch {
            val res = repository.getSellerInfo(id)
            res?.let {
                _sellerAccount.value = it
            }
        }
    }
}