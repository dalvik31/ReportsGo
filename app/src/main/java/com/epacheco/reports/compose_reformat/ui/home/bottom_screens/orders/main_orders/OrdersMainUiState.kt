package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders

import com.epacheco.reports.compose_reformat.model.orders.OrderMain

data class OrdersMainUiState(
    val orderMains: List<OrderMain> = emptyList(),  // List of notes to be displayed
    val isLoading: Boolean = false,       // Loading state
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null,
    val showImgEmptyList: Boolean? = null,
)