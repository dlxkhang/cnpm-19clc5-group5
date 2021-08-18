package com.example.muzee.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class ApiClient(
    private val loginApiService: LoginApiService
) {
    @POST("/api/account/login")
    suspend fun checklogin(@Body acc:Login_input): Response<Login_response>{
        return loginApiService.checklogin(acc)
    }
}