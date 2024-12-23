package com.example.dwitchapp.api

import com.example.dwitchapp.model.news.NewsResponse
import com.example.dwitchapp.model.OrdersResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface OrderApiService {
    @GET("orders?populate=*")
    suspend fun getAllOrders(
        @Header("Authorization") token: String
    ): OrdersResponse

//    @GET("orders/{id}")
//    suspend fun getOrderById(@Path("id") id: Long): Response<Order>


    // Ajout de l'endpoint pour les actualités
    @GET("posts?populate=*") // L'URL est "/posts" d'après votre spécification
    suspend fun getNews(
        @Header("Authorization") token: String
    ): NewsResponse

}
