package com.example.muzee.network.signup

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class SignUpApiClient(
    private val SignUpApiService:SignUpApiService
) {
    @POST("/api/account/signup/normal_user")
    suspend fun addNormalAccount(@Body nAccount:SignUp_nAccount_input): Response<String>{
        return SignUpApiService.addNormalAccount(nAccount)
    }
    @POST("/api/account/signup/seller")
    suspend fun addSellerAccount(@Body sAccount:SignUp_sAccount_input): Response<String>{
        return SignUpApiService.addSellerAccount(sAccount)
    }
}