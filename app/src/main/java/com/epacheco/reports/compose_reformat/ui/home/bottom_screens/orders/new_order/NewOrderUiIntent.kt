package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import com.epacheco.reports.compose_reformat.model.orders.Season

sealed class NewOrderUiIntent {
    data class CreateOrder(val mainOrderId: String, val orderSeason: Season?) : NewOrderUiIntent()
    data class DeleteOrder(val orderId: String, val mainOrderId: String) : NewOrderUiIntent()
    data object UpdateOrder : NewOrderUiIntent()
    data object HideDialogs : NewOrderUiIntent()
    data object RemoveClient : NewOrderUiIntent()
    data class GetClientById(val clientId: String?) : NewOrderUiIntent()
    data class GetOrderById(
        val orderMainId: String,
        val orderId: String,
        val callClientInfo: Boolean = false
    ) : NewOrderUiIntent()
}

