package com.example.muzee.signup.model

import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.signup.SignUp_nAccount_input
import com.example.muzee.network.signup.SignUp_sAccount_input

class SignUpRepository {
    suspend fun addNormalAccount(nAccount: SignUp_nAccount_input): String?{
        val request = NetworkLayer.SignUpApiClient.addNormalAccount(nAccount)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
    suspend fun addSellerAccount( sAccount: SignUp_sAccount_input):String?{
        val request = NetworkLayer.SignUpApiClient.addSellerAccount(sAccount)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
}