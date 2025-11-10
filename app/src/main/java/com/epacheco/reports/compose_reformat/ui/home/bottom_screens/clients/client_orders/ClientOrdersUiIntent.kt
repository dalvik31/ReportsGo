package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_orders


sealed class ClientOrdersUiIntent {
    data object LoadClientOrders : ClientOrdersUiIntent()
    data object HideDialogs : ClientOrdersUiIntent()
}

