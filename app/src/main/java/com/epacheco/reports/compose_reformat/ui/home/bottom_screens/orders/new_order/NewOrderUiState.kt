package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.orders.Order


data class NewOrderUiState(
    val isLoading: Boolean = false,
    val productStatus: Boolean = false,
    val productName: String = "",
    val productDescription: String = "",
    val productSize: String = "",
    val isProductSizeNumeric: Boolean = false,
    val productColor: String = "",
    val productColorCode: String? = null,
    val productGender: String = "",
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null,
    val orderToEdit: Order? = null,
    val client: Client? = null,
)