package com.example.dwitchapp.viewModel


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dwitchapp.model.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AccountViewModel : ViewModel() {

    // Flux pour les comptes
    private val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> = _accounts

    // Indicateur de chargement
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Gestion des erreurs
    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError

    // Fonction pour récupérer les comptes
    fun fetchAccounts() {
        viewModelScope.launch {
            _isLoading.value = true
            _hasError.value = false
            try {
                // Simuler une requête réseau
                val fetchedAccounts = listOf(
                    Account(1, "Alice", "alice@example.com"),
                    Account(2, "Bob", "bob@example.com")
                )
                _accounts.value = fetchedAccounts
            } catch (e: Exception) {
                _hasError.value = true
            } finally {
                _isLoading.value = false
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
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nom : ${account.name}")
                Text("Email : ${account.email}")
            }
        }
    }
}
