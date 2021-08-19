package com.example.muzee.network.login

import android.accounts.Account
import com.example.muzee.data.NormalUser
import com.example.muzee.data.Seller
import com.example.muzee.network.login.Login_input
import com.example.muzee.network.login.Login_response
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

        @POST("/api/account/login")
        suspend fun checklogin(@Body acc: Login_input): Response<Login_response>
        @POST("/api/account/normal_user/info")
        suspend fun getNormalUserInfo(@Body id_response:getUser_response ):Response<NormalUser>
        @POST("/api/account/seller/info")
        suspend fun getSellerInfo(@Body id_response:getUser_response):Response<Seller>
}