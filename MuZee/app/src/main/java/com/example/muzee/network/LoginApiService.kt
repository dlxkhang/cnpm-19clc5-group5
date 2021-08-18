package com.example.muzee.network

import android.accounts.Account
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {

        @POST("/api/account/login")
        suspend fun checklogin(@Body acc:Login_input): Response<Login_response>

        @GET("getAccount")
        suspend fun getAccount(): Account
}