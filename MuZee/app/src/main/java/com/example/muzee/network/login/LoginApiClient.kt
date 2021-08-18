package com.example.muzee.network.login

import com.example.muzee.network.login.LoginApiService
import com.example.muzee.network.login.Login_input
import com.example.muzee.network.login.Login_response
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class LoginApiClient(
    private val loginApiService: LoginApiService
) {
    @POST("/api/account/login")
    suspend fun checklogin(@Body acc: Login_input): Response<Login_response>{
        return loginApiService.checklogin(acc)
    }
}