package com.example.dwitchapp.ui.theme

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dwitchapp.R
import com.example.dwitchapp.viewModel.OrderViewModel

class OrderActivity : AppCompatActivity() {
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // Observez les données dans l'UI
        lifecycleScope.launchWhenStarted {
            orderViewModel.orders.collect { orders ->
                // Mettez à jour l'UI avec les commandes récupérées
            }
        }
    }
}