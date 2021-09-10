package com.example.muzee.network.seller.product

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class ProductApiClient(
    private val productApiService: ProductApiService
) {
    @POST("/api/product/seller")
    suspend fun getSellerProduct(@Body accountID:ID_Request ):Response<List<ProductSeller>>{
        return productApiService.getSellerProduct(accountID)
    }
    @POST("/api/product/add")
    suspend fun addSellerProduct(@Body productSeller:ProductSeller): Response<AckResponse>{
        return productApiService.addSellerProduct(productSeller)
    }
    @POST("/api/product/edit")
    suspend fun editSellerProduct(@Body productSeller: ProductSeller): Response<AckResponse>{
        return productApiService.editSellerProduct(productSeller)
    }
    @POST("/api/product/delete")
    suspend fun deleteSellerProduct(@Body productID:ID_Request): Response<AckResponse>{
        return productApiService.deleteSellerProduct(productID)
    }
}