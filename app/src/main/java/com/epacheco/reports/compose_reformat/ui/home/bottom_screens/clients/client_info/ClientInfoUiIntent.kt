package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import java.io.File


sealed class ClientInfoUiIntent {
    data class LoadTransactions(val clientId: String) :
        ClientInfoUiIntent()

    data object HideDialogs : ClientInfoUiIntent()
}

