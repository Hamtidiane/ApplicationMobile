package com.example.dwitchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dwitchapp.model.Ingredient
import com.example.dwitchapp.model.Ingredientkind
import com.example.dwitchapp.model.Order
import com.example.dwitchapp.model.Store
import com.example.dwitchapp.model.color
import com.example.dwitchapp.model.emoji
import com.example.dwitchapp.model.orders
import com.example.dwitchapp.ui.theme.DwitchAppTheme
import com.example.ui.theme.OpenColors
import com.example.ui.theme.OpenColorsPalette

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
                    OrderList(orders = orders) // Appelle l'écran principal
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(orders: List<Order>) {
    // Utilise Scaffold pour structurer la page avec un TopAppBar et un FloatingActionButton

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Liste des commandes")
                }
            )
        },
        bottomBar= {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }





    ) { innerPadding ->
        OrderList(orders = com.example.dwitchapp.model.orders, modifier = Modifier.padding(innerPadding))

    }
}

@Composable
fun OrderList(orders: List<Order>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)) {
        items(orders) { order ->
            OrderItem(order = order)
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 4.dp,
        color = OpenColors.orange0,
        modifier = Modifier
            .fillMaxWidth()

    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Commande ID: ${order.documentID}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Prix: ${order.price} €", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Message: ${order.cookMessage ?: "Aucun message"}", style = MaterialTheme.typography.bodyMedium)
            IngredientList(order.ingredients ?: emptyList())
            ProgressBar(order)
            Text(
                text = "${order.store?.name} - ${order.store?.city}  ${order.store?.zipCode}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )


        }
    }
}

@Composable
fun IngredientItem(ingredient : Ingredient) {

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),

        color = ingredient.ingredientKind.color(),

        ) {

        Text(
            text = ingredient.name ?: "",
            color = Color.Black,

            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 26.dp)

        )
        Text(
            text = ingredient.ingredientKind.emoji(),


            )
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
fun ProgressBar(order: Order) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {


        LinearProgressIndicator(
            progress = {order.progress?.div(100f) ?: 0f},
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary
        )

    }
}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DwitchAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            OrderScreen(orders = orders)

        }
    }
}

