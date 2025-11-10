package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.create_client

sealed class CreateClientUiEffect {
    data object NavigateBack : CreateClientUiEffect()
}
