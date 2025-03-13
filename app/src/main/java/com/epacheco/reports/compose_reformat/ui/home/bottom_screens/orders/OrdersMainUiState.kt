package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import com.epacheco.reports.compose_reformat.model.orders.Order

data class OrdersMainUiState(
    val orders: List<Order> = emptyList(),  // List of notes to be displayed
    val isLoading: Boolean = false,       // Loading state
    val errorMessage: String? = null,
    val showImgEmptyList: Boolean? = null
)