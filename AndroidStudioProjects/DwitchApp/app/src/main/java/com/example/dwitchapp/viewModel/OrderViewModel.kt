import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dwitchapp.api.ApiClient
import com.example.dwitchapp.model.Order
import kotlinx.coroutines.launch
import timber.log.Timber

class OrderViewModel : ViewModel() {

    private val _orders = mutableStateOf<List<Order>>(emptyList())
    val orders: State<List<Order>> get() = _orders


    init {
        fetchOrders()
    }
    fun fetchOrders() {
        viewModelScope.launch {
            try {
                val token = "Bearer 49b70f996ffbb654be996f8604d118bfca7624ced27749df6f4fdcac30b7009da1ba63ef7d6b91c8ca814baf88955daba2804396ab3b8cd2c03b50a1f96ff330032d2fbc2238338b4f7e25bff9e852b002c26ecca02fbf1e8e261cf6e0cdb00c042e35b33f64dda3522c3178ba1edb22b9daba42b51c1c8355309fd475b5d92b" // Normally you get this token from your auth process
                Log.d("API", "Envoi de la requête pour récupérer les commandes.")
                val response = ApiClient.dwitchService.getAllOrders(token)
                Timber.d("$response")
                val orderList = response.data // This is List<Order>
                _orders.value = orderList

//               if (response.isSuccessful) {
//                    Log.d("API", "Réponse reçue: ${response.body()}")
//                    _orders.value = response.body()
//                } else {
//                    Log.d("API", "Réponse reçue: ${response.body()}")
//                    // Gérer les erreurs de réponse
//                    _orders.value = null
//                }
            } catch (e: Exception) {
                Log.e("API", "Erreur lors de la requête: ${e.message}")
                // Gérer les exceptions réseau
                Timber.d("Error fetching orders: ${e.message}")
            }
        }

    }
}
