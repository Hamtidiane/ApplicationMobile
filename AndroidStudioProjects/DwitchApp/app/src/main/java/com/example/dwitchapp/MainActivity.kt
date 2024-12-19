package com.example.dwitchapp

import OrderViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dwitchapp.model.Ingredient
import com.example.dwitchapp.model.Order
import com.example.dwitchapp.model.color
import com.example.dwitchapp.model.emoji
import com.example.dwitchapp.ui.theme.DwitchAppTheme
import com.example.ui.theme.OpenColors
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
                    OrderScreen() // Appelle l'écran principal
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("Liste des commandes") }
            )
        },
        bottomBar = {
            BottomNavigationBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        OrderList(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun OrderList(viewModel: OrderViewModel = viewModel(), modifier: Modifier = Modifier) {
    val orders by viewModel.orders
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
fun BottomNavigationBar() {
    var selectedItem by remember { mutableStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Compte") },
            label = { Text("Compte") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Fastfood, contentDescription = "Commander") },
            label = { Text("Commander") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.NewReleases, contentDescription = "News") },
            label = { Text("News") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    DwitchAppTheme {
        OrderScreen()
    }
}
