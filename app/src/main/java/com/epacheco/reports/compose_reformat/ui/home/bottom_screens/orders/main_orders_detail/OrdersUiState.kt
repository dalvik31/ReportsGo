package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import com.epacheco.reports.compose_reformat.model.orders.Order

data class OrdersUiState(
    val orders: List<Order> = emptyList(),  // List of notes to be displayed
    val isLoading: Boolean = false,       // Loading state
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null,
    val showImgEmptyList: Boolean? = null
)