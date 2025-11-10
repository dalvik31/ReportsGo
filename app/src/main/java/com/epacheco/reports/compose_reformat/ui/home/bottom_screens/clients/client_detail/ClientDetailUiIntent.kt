package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_detail

sealed class ClientDetailUiIntent {
    data class LoadClient(val clientId: String) : ClientDetailUiIntent()
    data class UpdateAmountPayClient(val clientId: String) : ClientDetailUiIntent()
    data object HideDialogs : ClientDetailUiIntent()

}