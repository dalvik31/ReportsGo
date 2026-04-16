package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.orders.Order

data class ClientInfoUiState(
    val clientTransactions: List<Sale> = emptyList(),
    val clientOrders: List<Order> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)