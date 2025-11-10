package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.create_client

import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.sales.Sale

data class CreateClientUiState(
    val client: Client? = null,
    val clientName: String = "",
    val clientLastName: String = "",
    val clientInfo: String = "",
    val clientPhone: String = "",
    val clientCredit: String = "",
    val clientTransactions: List<Sale> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: Int? = null,
)