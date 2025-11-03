package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales.SalesUiIntent

sealed class NewOrderUiIntent {
    data class CreateOrder(val mainOrderId: String, val orderSeason: Season?) : NewOrderUiIntent()
    data class DeleteOrder(val orderId: String, val mainOrderId: String) : NewOrderUiIntent()
    data class UpdateOrder(val order: Order) : NewOrderUiIntent()
    data object HideDialogs : NewOrderUiIntent()
    data class GetClientById(val clientId: String?) : NewOrderUiIntent()
}

