package com.example.muzee.network.signup

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApiService {
    @POST("/api/account/signup/normal_user")
    suspend fun addNormalAccount(@Body nAccount:SignUp_nAccount_input):Response<String>
    @POST("/api/account/signup/seller")
    suspend fun addSellerAccount(@Body sAccount:SignUp_sAccount_input):Response<String>
}