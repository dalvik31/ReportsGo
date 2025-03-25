package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import com.epacheco.reports.compose_reformat.model.orders.OrderStatus

sealed class OrdersUiIntent {
    data class LoadOrders(val mainOrderId: String) : OrdersUiIntent()
    data class CreateOrder(val mainOrderId: String) : OrdersUiIntent()
    data class GoToCreateOrder(val mainOrderId: String) : OrdersUiIntent()
    data class DeleteOrder(val orderId: String) : OrdersUiIntent()
    data class UpdateStatusOrder(val orderId: String, val orderStatus: OrderStatus) :
        OrdersUiIntent()

    data object HideDialogs : OrdersUiIntent()
}

