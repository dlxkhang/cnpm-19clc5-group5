package com.example.muzee.login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzee.data.NormalUser
import com.example.muzee.data.Seller
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.login.Login_input
import com.example.muzee.network.login.getUser_response
import kotlinx.coroutines.launch
class LoginViewModel : ViewModel() {
    enum class ApiStatus { ERROR, WRONGUSERNAME,WRONGPASSWORD,SUCCESS}
    private val _userAccount = MutableLiveData<NormalUser>()
    val userAccount:LiveData<NormalUser> = _userAccount
    private val _sellerAccount = MutableLiveData<Seller>()
    val sellerAccount:LiveData<Seller> = _sellerAccount
    private val _accountID = MutableLiveData<String>()
    val accountID:LiveData<String> = _accountID

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()
    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status
    fun checkLogin(username: String, password: String) {
        viewModelScope.launch(){
            val login_input = Login_input(username,password)
            try {
                val request = NetworkLayer.LoginApiClient.checklogin(login_input)
                if(request.isSuccessful){
                    val data = request.body()
                    when(data?.ack){
                        "account_not_exist"->{
                            _status.value = ApiStatus.WRONGUSERNAME
                        }
                        "account_not_valid"->{
                            _status.value = ApiStatus.WRONGPASSWORD
                        }
                        "seller_account_valid"->{
                            _status.value = ApiStatus.SUCCESS
                            _accountID.value = data.ID
                            val id_input = getUser_response(data.ID.toString())
                            val seller_request = NetworkLayer.LoginApiClient.getSellerInfo(id_input)
                            if(seller_request.isSuccessful){
                                _sellerAccount.value = seller_request.body()
                            }
                        }
                        "normal_user_account_valid"->{
                            _status.value = ApiStatus.SUCCESS
                            _accountID.value = data.ID
                            val id_input = getUser_response(data.ID.toString())
                            val normal_request = NetworkLayer.LoginApiClient.getNormalUserInfo(id_input)
                            if(normal_request.isSuccessful){
                                _userAccount.value = normal_request.body()
                            }
                        }
                    }
                }
            } catch (e:Exception){
                _status.value = ApiStatus.ERROR
            }
        }
    }
}