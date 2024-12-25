package com.example.dwitchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.dwitchapp.model.Account
import com.example.dwitchapp.ui.theme.DwitchAppTheme
import com.example.dwitchapp.viewModel.AccountViewModel
import com.example.dwitchapp.viewModel.NewsList
import com.example.dwitchapp.viewModel.NewsViewModel
import com.example.dwitchapp.viewModel.OrdersViewModel




class MainActivity : ComponentActivity() {
    private val newsViewModel: NewsViewModel by viewModels()
    private val ordersViewModel: OrdersViewModel by viewModels()
    private val accountViewModel: AccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DwitchAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(newsViewModel, ordersViewModel, accountViewModel)
                }
            }
        }
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(orderViewModel: OrdersViewModel) {
    val orders by orderViewModel.orders.collectAsState()
    val isLoading by orderViewModel.isLoading.collectAsState()
    val hasError by orderViewModel.hasError.collectAsState()

    LaunchedEffect(Unit) {


            orderViewModel.fetchOrders() // Charger les commandes au démarrage        } catch (e: Exception) {
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
                    IconButton(onClick = { orderViewModel.fetchOrders()  }) {
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
            orderViewModel.OrderList(orders = orders, modifier = Modifier.padding(innerPadding))
        }
    }
}
@Composable
fun NewsScreen(newsViewModel: NewsViewModel) {
    val news by newsViewModel.news.collectAsState()
    val isLoading by newsViewModel.isLoading.collectAsState()
    val hasError by newsViewModel.hasError.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.loadNews()  // Chargez les nouvelles au démarrage
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            hasError -> {
                Text(
                    text = "Une erreur est survenue lors du chargement des actualités.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            news.isEmpty() -> { // Vérifiez si la liste des news est vide
                Text(
                    text = "Aucune actualité disponible.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> {
                NewsList(news = news)  // Affichage de la liste des nouvelles
            }
        }
    }
}
@Composable
fun AccountScreen(accountViewModel: AccountViewModel) {
    val accounts by accountViewModel.accounts.collectAsState()
    val isLoading by accountViewModel.isLoading.collectAsState()
    val hasError by accountViewModel.hasError.collectAsState()

    // Charger les données une seule fois
    LaunchedEffect(Unit) {
        accountViewModel.fetchAccounts()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            isLoading -> CircularProgressIndicator()
            hasError -> Text("Erreur lors du chargement des comptes")
            accounts.isEmpty() ->{
                Text("Aucun compte trouvé")
            }
            else -> {
                AccountList(accounts)
            }
        }
    }
}

@Composable
fun AccountList(accounts: List<Account>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(accounts.size) { index ->
            val account = accounts[index]
            AccountCard(account)
        }
    }
}

@Composable
fun AccountCard(account: Account) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(4.dp)    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nom : ${account.name}")
            Text("Email : ${account.email}")
        }
    }
}


@Composable
fun MainScreen(newsViewModel: NewsViewModel, ordersViewModel: OrdersViewModel, accountViewModel: AccountViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "orders",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("orders") { OrderScreen(ordersViewModel) }
            composable("compte") { AccountScreen(accountViewModel) }
            composable("news") { NewsScreen(newsViewModel) }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Triple("compte", "Compte", Icons.Filled.AccountCircle),
        Triple("orders", "Commander", Icons.Filled.Fastfood),
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



@Preview(showBackground = true)
@Composable
fun PreviewOrderScreen() {
    DwitchAppTheme {
        OrderScreen(
            orderViewModel = OrdersViewModel()
        )
    }
}
