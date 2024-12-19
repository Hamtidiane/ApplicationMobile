package com.example.dwitchapp

import OrderViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.runtime.setValue


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
                    OrderList() // Appelle l'écran principal
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen() {
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
            BottomNavigationBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }





    ) { innerPadding ->
        OrderList( modifier = Modifier.padding(innerPadding))

    }
}
@Composable
fun OrderList(viewModel: OrderViewModel = viewModel(), modifier: Modifier = Modifier) {
    val orders by viewModel.orders
    LazyColumn(modifier = modifier.padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)) {
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
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Vérification et conversion de l'Instant depuis un String
            val formattedDate = order.placedAt?.let {
                // Convertir le String en Instant
                val instant = Instant.parse(it) // Parse ISO 8601 format
                // Convertir Instant en ZonedDateTime en utilisant le fuseau horaire local
                val localDateTime = instant.atZone(ZoneId.systemDefault())
                // Formater la date
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm")
                "Le ${localDateTime.format(formatter)}"
            } ?: "Date inconnue"

            // Affichage de la date et du prix dans une ligne
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formattedDate)
                Text(text = "${order.price ?: "Prix inconnu"} €", style = MaterialTheme.typography.bodyMedium)
            }

            // Autres informations
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Texte "Progression" à gauche
            Text(
                text = "Progression",
                style = MaterialTheme.typography.bodyMedium
            )

            // Barre de progression
            LinearProgressIndicator(
                progress = { (order.progress?.div(100f) ?: 0f) },
                modifier = Modifier
                    .weight(1f) // Prendre tout l'espace restant
                    .height(6.dp),
                color = MaterialTheme.colorScheme.primary,
            )

            // Texte "100%" à droite
            Text(
                text = "100%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@Composable
fun BottomNavigationBar() {
    var selectedItem by remember { mutableStateOf(0) }

    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Compte")
            },
            label = {
                Text(text = "Compte")
            },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )
        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.Fastfood, contentDescription = "Commander")
            },
            label = {
                Text(text = "Commander")
            },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 }
        )
        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.NewReleases, contentDescription = "News")
            },
            label = {
                Text(text = "News")
            },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 }
        )
    }
}

@Composable
fun BottomNavigationItem(icon: @Composable () -> Unit, label: @Composable () -> Unit, selected: Boolean, onClick: () -> Unit) {

}

@Composable
fun BottomNavigation(modifier: Modifier, backgroundColor: Color, content: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
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
            OrderScreen()

        }
    }
}

