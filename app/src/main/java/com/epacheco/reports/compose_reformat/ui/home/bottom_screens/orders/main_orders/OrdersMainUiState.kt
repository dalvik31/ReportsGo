package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders

import com.epacheco.reports.compose_reformat.model.orders.OrderMain

data class OrdersMainUiState(
    val orderMains: List<OrderMain> = emptyList(),
    val listName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null,
)