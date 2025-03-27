package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season

sealed class OrdersUiIntent {
    data class LoadOrders(val mainOrderId: String) : OrdersUiIntent()
    data class CreateOrder(val mainOrderId: String, val orderSeason: Season?) : OrdersUiIntent()
    data class DeleteOrder(val orderId: String, val mainOrderId: String) : OrdersUiIntent()
    data class UpdateStatusOrder(
        val orderId: String,
        val mainOrderId: String,
        val orderStatus: OrderStatus
    ) :
        OrdersUiIntent()

    data object HideDialogs : OrdersUiIntent()
}

