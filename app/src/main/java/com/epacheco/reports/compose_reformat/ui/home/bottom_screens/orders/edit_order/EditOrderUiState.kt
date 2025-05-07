package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.edit_order


data class EditOrderUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null
)