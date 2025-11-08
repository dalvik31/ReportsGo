package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.model.clients.Client

data class DetailClientUiState(
    val clientDetail: Client? = null,
    val clientTransactions: List<Sale> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: Int? = null,
)