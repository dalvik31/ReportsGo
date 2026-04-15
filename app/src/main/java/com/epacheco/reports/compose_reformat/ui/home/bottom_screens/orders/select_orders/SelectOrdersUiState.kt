package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.select_orders

import com.epacheco.reports.compose_reformat.model.orders.OrderMain

data class SelectOrdersUiState(
    val orderMainList: List<OrderMain> = emptyList(),
    val orderMainId: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
)