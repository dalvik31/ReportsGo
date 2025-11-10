package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product


data class SalesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val client: Client? = null,
    val product: Product? = null,
    val cartProducts: List<Product> = emptyList(),
    val totalSale: Double? = null,
    val isCreditSale: Boolean = false,
    val newLimit: Double = 0.0,
    val newLimitUsed: Double = 0.0,
    val successOperationMsg: Int? = null,
)