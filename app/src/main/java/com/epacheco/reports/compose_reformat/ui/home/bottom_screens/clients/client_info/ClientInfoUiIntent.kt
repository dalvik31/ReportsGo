package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info


sealed class ClientInfoUiIntent {
    data class LoadTransactions(val clientId: String) :
        ClientInfoUiIntent()

    data class LoadOrders(val clientId: String) :
        ClientInfoUiIntent()

    data object HideDialogs : ClientInfoUiIntent()
}

