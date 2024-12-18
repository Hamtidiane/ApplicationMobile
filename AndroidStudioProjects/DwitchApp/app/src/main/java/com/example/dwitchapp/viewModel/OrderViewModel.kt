import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dwitchapp.model.Order
import com.example.dwitchapp.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: OrderRepository) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>?>(null)
    val orders: StateFlow<List<Order>?> get() = _orders

    fun fetchOrders() {
        viewModelScope.launch {
            try {
                Log.d("API", "Envoi de la requête pour récupérer les commandes.")
                val response = repository.getAllOrders()
                if (response.isSuccessful) {
                    Log.d("API", "Réponse reçue: ${response.body()}")
                    _orders.value = response.body()
                } else {
                    Log.d("API", "Réponse reçue: ${response.body()}")
                    // Gérer les erreurs de réponse
                    _orders.value = null
                }
            } catch (e: Exception) {
                Log.e("API", "Erreur lors de la requête: ${e.message}")
                // Gérer les exceptions réseau
                _orders.value = null
            }
        }

    }
}
