package com.example.dwitchapp.api

import com.example.dwitchapp.model.Order
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApiService {
    @GET("orders")
    suspend fun getOrders(): List<Order>

    @GET("orders/{id}")
    suspend fun getOrderById(@Path("id") id: Long): Order

    @POST("orders")
    suspend fun createOrder(@Body order: Order): Response<Order>
}
