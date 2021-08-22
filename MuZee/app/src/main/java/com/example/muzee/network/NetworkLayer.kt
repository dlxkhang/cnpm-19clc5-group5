package com.example.muzee.network

import com.example.muzee.network.login.LoginApiClient
import com.example.muzee.network.login.LoginApiService
import com.example.muzee.network.seller.order.OrderApiClient
import com.example.muzee.network.seller.order.OrderApiService
import com.example.muzee.network.signup.SignUpApiClient
import com.example.muzee.network.signup.SignUpApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {
    private const val BASE_URL = "http://192.168.11.109:3000/"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()
    val loginRetrofitService: LoginApiService by lazy{
        retrofit.create(LoginApiService::class.java)
    }
    val signupRetrofitService:SignUpApiService by lazy{
        retrofit.create(SignUpApiService::class.java)
    }
    val LoginApiClient = LoginApiClient(loginRetrofitService)
    val SignUpApiClient = SignUpApiClient(signupRetrofitService)

    val orderRetrofitSeller: OrderApiService by lazy{
        retrofit.create(OrderApiService::class.java)
    }
    val OrderApiClient = OrderApiClient(orderRetrofitSeller)
}