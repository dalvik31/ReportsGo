package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import java.io.File


sealed class ClientUiIntent {
    data class LoadClients(val clientId: String? = null) : ClientUiIntent()

    data class Error(val msgError: String? = null) : ClientUiIntent()

}

