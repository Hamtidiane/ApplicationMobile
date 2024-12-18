package com.example.dwitchapp.repository

import com.example.dwitchapp.api.OrderApiService
import com.example.dwitchapp.model.Order
import retrofit2.Response

class OrderRepository(private val apiService: OrderApiService) {

    // Méthode pour récupérer toutes les commandes
    suspend fun getAllOrders(): List<Order> {
        return apiService.getOrders()
    }

    // Méthode pour récupérer une commande par ID
    suspend fun getOrderById(id: Long): Order {
        return apiService.getOrderById(id)
    }

    // Méthode pour créer une nouvelle commande
    suspend fun createOrder(order: Order): Response<Order> {
        return apiService.createOrder(order)
    }
}