package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_detail

sealed class ClientDetailUiEffect {
    data object NavigateBack : ClientDetailUiEffect()
}