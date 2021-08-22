package com.example.muzee.network.seller.order

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class OrderApiClient(
    private val orderApiService: OrderApiService
) {
    @POST("/api/order_seller")
    suspend fun getOrderSeller(@Body Order_req:Order_request): Response<List<Order_responseItem>>{
        return orderApiService.getOrderSeller(Order_req)
    }
}