package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.create_client


sealed class CreateClientUiIntent {
    data class LoadClient(val clientId: String) :
        CreateClientUiIntent()
    data class UpdateClient(val clientId: String) : CreateClientUiIntent()
    data object CreateClient : CreateClientUiIntent()
    data class DeleteClient(val clientId: String) : CreateClientUiIntent()
    data object HideDialogs : CreateClientUiIntent()

}

