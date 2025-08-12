package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view

import com.epacheco.reports.compose_reformat.model.clients.Client


data class ClientsUiState(
    val listClients: List<Client> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)