package com.example.muzee.network.seller.product

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductApiService {
    @POST("/api/product/seller")
    suspend fun getSellerProduct(@Body SID:ID_Request ):Response<List<ProductSeller>>
    @POST("/api/product/add")
    suspend fun addSellerProduct(@Body productSeller:ProductSeller):Response<AckResponse>
    @POST("/api/product/edit")
    suspend fun editSellerProduct(@Body productSeller: ProductSeller):Response<AckResponse>
    @POST("/api/product/delete")
    suspend fun deleteSellerProduct(@Body productID:ID_Request):Response<AckResponse>
}