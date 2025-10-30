package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiEffect

sealed class ClientDetailUiEffect {
    data object NavigateBack : ClientDetailUiEffect()
}
