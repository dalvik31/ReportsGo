package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_orders

import com.epacheco.reports.compose_reformat.model.orders.OrderMain

data class ClientOrdersUiState(
    val orderMainList: List<OrderMain> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)