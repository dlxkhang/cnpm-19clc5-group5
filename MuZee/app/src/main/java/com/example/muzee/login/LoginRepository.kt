package com.example.muzee.login

import com.example.muzee.network.login.Login_input
import com.example.muzee.network.login.Login_response
import com.example.muzee.network.NetworkLayer

class LoginRepository {
    suspend fun checklogin(acc: Login_input): Login_response?{
        val request = NetworkLayer.LoginApiClient.checklogin(acc)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
}