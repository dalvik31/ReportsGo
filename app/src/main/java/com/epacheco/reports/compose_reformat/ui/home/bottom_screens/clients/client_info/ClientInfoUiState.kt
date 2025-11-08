package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.model.clients.Client

data class ClientInfoUiState(
    val clientTransactions: List<Sale> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)