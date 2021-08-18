package com.example.muzee.login

import com.example.muzee.network.Login_input
import com.example.muzee.network.Login_response
import com.example.muzee.network.NetworkLayer

class LoginRepository {
    suspend fun checklogin(acc:Login_input):Login_response?{
        val request = NetworkLayer.ApiClient.checklogin(acc)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
}