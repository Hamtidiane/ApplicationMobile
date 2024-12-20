package com.example.dwitchapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dwitchapp.api.ApiClient
import com.example.dwitchapp.model.Ingredient
import com.example.dwitchapp.model.Order
import com.example.dwitchapp.model.color
import com.example.dwitchapp.model.emoji
import com.example.dwitchapp.ui.theme.DwitchAppTheme
import com.example.ui.theme.OpenColors
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DwitchAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

suspend fun fetchOrders(): List<Order>? {
    return try {
        val token = BuildConfig.apiKey // Assurez-vous que `apiKey` est bien configuré
        Log.d("API", "Envoi de la requête pour récupérer les commandes.")
        val response = ApiClient.dwitchService.getAllOrders("Bearer $token")
        response.data
    } catch (e: Exception) {
        Log.e("API", "Erreur lors de la requête: ${e.message}")
        null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen() {
    var shouldRefreshData by remember { mutableStateOf(false) }
    var orderList by remember { mutableStateOf(emptyList<Order>()) }
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    LaunchedEffect(shouldRefreshData) {
        isLoading = true
        hasError = false
        try {
            orderList = fetchOrders() ?: emptyList()
        } catch (e: Exception) {
            hasError = true
            Timber.e("Erreur de récupération des commandes : ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Mes commandes") },
                navigationIcon = {
                    IconButton(onClick = { shouldRefreshData = !shouldRefreshData }) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "Refresh content")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Ajouter une commande */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (hasError) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Une erreur est survenue lors du chargement des commandes.",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            OrderList(orders = orderList, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "orders",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("orders") { OrderScreen() }
            composable("compte") { CompteScreen() }
            composable("news") { NewsScreen() }
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
                progress = (order.progress?.div(100f) ?: 0f),
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp),
                color = MaterialTheme.colorScheme.primary,
            )
            Text(text = "100%", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Triple("orders", "Commandes", Icons.Filled.Fastfood),
        Triple("compte", "Compte", Icons.Filled.AccountCircle),
        Triple("news", "News", Icons.Filled.NewReleases)
    )

    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.third, contentDescription = item.second) },
                label = { Text(item.second) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.first) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun CompteScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Écran Compte", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun NewsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Écran News", style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    DwitchAppTheme {
        OrderScreen()
    }
}
