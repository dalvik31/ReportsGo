package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import android.net.Uri
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product
import com.google.firebase.auth.FirebaseUser


data class SalesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val client: Client? = null,
    val product: Product? = null,
    val cartProducts: List<Product> = emptyList(),
    val totalSale: Double? = null
)