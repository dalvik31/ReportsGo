package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients


sealed class ClientUiIntent {
    data class LoadClients(val clientId: String? = null) : ClientUiIntent()

    data class Error(val msgError: String? = null) : ClientUiIntent()

}

