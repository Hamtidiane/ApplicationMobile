package com.example.dwitchapp.viewModel

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dwitchapp.BuildConfig
import com.example.dwitchapp.model.Order
import com.example.dwitchapp.api.ApiClient
import com.example.dwitchapp.model.Ingredient
import com.example.dwitchapp.model.color
import com.example.dwitchapp.model.emoji
import com.example.ui.theme.OpenColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class OrdersViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError

    fun fetchOrders() {
        viewModelScope.launch {
            _isLoading.value = true
            _hasError.value = false
            try {
                val token = BuildConfig.apiKey // Assurez-vous que `apiKey` est bien configuré
                Timber.d("Fetching orders from API")
                val response = ApiClient.dwitchService.getAllOrders("Bearer $token")
                _orders.value = response.data ?: emptyList()
            } catch (e: Exception) {
                _hasError.value = true
                Timber.e("Error fetching orders: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    @Composable
    fun OrderList(orders: List<Order>, modifier: Modifier = Modifier) {
        LazyColumn(
            modifier = modifier.padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(orders) { order ->
                OrderItem(order = order)
            }
        }
    }

    @SuppressLint("NewApi")
    @Composable
    fun OrderItem(order: Order) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 4.dp,
            color = OpenColors.orange0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val formattedDate = order.placedAt?.let {
                    val instant = Instant.parse(it)
                    val localDateTime = instant.atZone(ZoneId.systemDefault())
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm")
                    "Le ${localDateTime.format(formatter)}"
                } ?: "Date inconnue"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(formattedDate)
                    Text(text = "${order.price ?: "Prix inconnu"} €", style = MaterialTheme.typography.bodyMedium)
                }

                Text(text = "Message: ${order.cookMessage ?: "Aucun message"}", style = MaterialTheme.typography.bodyMedium)
                IngredientList(order.ingredients ?: emptyList())
                ProgressBar(order)
                Text(
                    text = "${order.store?.name} - ${order.store?.city} ${order.store?.zipCode}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
    @Composable
    fun IngredientList(
        ingredients: List<Ingredient>,
        modifier: Modifier = Modifier
    ) {
        LazyRow(
            modifier = modifier.fillMaxWidth(),
        ) {
            items(ingredients) { ingredient ->
                IngredientItem(ingredient = ingredient)
            }
        }
    }
    @Composable
    fun IngredientItem(ingredient: Ingredient) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            color = ingredient.ingredientKind.color(),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ingredient.ingredientKind.emoji(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = ingredient.name ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    @Composable
    fun ProgressBar(order: Order) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Progression", style = MaterialTheme.typography.bodyMedium)
                LinearProgressIndicator(
                    progress = { (order.progress?.div(100f) ?: 0f) },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(text = "100%", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }

}
