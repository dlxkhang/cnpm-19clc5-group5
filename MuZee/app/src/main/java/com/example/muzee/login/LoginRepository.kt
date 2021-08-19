package com.example.muzee.login

import com.example.muzee.data.NormalUser
import com.example.muzee.data.Seller
import com.example.muzee.network.login.Login_input
import com.example.muzee.network.login.Login_response
import com.example.muzee.network.NetworkLayer
import com.example.muzee.network.login.getUser_response
import retrofit2.Response
import retrofit2.http.Body

class LoginRepository {
    suspend fun checklogin(acc: Login_input): Login_response?{
        val request = NetworkLayer.LoginApiClient.checklogin(acc)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
    suspend fun getNormalUserInfo( id:String ): NormalUser? {
        val id_input = getUser_response(id)
        val request = NetworkLayer.LoginApiClient.getNormalUserInfo(id_input)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
    suspend fun getSellerInfo( id:String):Seller?{
        val id_input = getUser_response(id)
        val request = NetworkLayer.LoginApiClient.getSellerInfo(id_input)
        if(request.isSuccessful){
            return request.body()!!
        }
        return null
    }
}