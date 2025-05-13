package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order


data class NewOrderUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null
)