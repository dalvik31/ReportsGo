package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients

import com.epacheco.reports.compose_reformat.model.clients.Client


data class ClientsUiState(
    val listClients: List<Client> = emptyList(),
    val clientName: String = "",
    val clientPhone: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)