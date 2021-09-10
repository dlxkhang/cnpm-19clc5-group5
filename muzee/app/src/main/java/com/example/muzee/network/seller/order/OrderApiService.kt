package com.example.muzee.network.seller.order

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApiService {
    @POST("/api/order_seller")
    suspend fun getOrderSeller(@Body Order_req: Order_request):Response<List<Order_responseItem>>

    @POST("/api/order_seller/accept")
    suspend fun acceptOrder(@Body acceptOrderRequest: FunctionOrderRequest):Response<AckResponse>

    @POST("/api/order_seller/cancel")
    suspend fun cancleOrder(@Body cancleOrderRequese:FunctionOrderRequest):Response<AckResponse>
}