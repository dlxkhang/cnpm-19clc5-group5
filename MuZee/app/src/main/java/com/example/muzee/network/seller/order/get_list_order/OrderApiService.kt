package com.example.muzee.network.seller.order.get_list_order

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApiService {
    @POST("/api/order_seller")
    suspend fun getOrderSeller(@Body Order_req: Order_request):Response<List<Order_responseItem>>
}