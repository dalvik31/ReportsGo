package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.orders

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import java.io.File


sealed class ClientOrdersUiIntent {
    data object LoadClientOrders : ClientOrdersUiIntent()
    data object HideDialogs : ClientOrdersUiIntent()
}

