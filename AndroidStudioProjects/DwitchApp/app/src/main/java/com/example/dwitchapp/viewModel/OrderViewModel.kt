package com.example.dwitchapp.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dwitchapp.model.Order
import com.example.dwitchapp.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    // StateFlow pour observer les commandes
    private val _orders = MutableStateFlow<List<Order>?>(null)
    val orders: StateFlow<List<Order>?> get() = _orders

    // Charger toutes les commandes
    fun fetchOrders() {
        viewModelScope.launch {
            try {
                val response = repository.getAllOrders()
                if (response.isSuccessful) {
                    _orders.value = response.body()
                } else {
                    // Gestion des erreurs
                    _orders.value = null
                }
            } catch (e: Exception) {
                // Gestion des exceptions réseau
                _orders.value = null
            }
        }
    }
}