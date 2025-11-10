package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_detail

import com.epacheco.reports.compose_reformat.model.clients.Client

data class DetailClientUiState(
    val client: Client? = null,
    val clientAmount: String = "",
    val clientConcept: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: Int? = null,
)