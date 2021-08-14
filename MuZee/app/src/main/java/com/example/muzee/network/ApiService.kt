package com.example.muzee.network

import android.accounts.Account
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private const val BASE_URL = ""
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface LoginApiService{
    @POST("checklogin")
    fun checklogin(@Header("Authorization") autoToken:String): Call<String>
    @GET("getAccount")
    suspend fun getAccount():Account
}
object LoginApi{
    val loginRetrofitService: LoginApiService by lazy{
        retrofit.create(LoginApiService::class.java)
    }
}