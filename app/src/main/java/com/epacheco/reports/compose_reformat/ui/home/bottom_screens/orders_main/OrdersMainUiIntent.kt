package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main

import com.epacheco.reports.compose_reformat.model.orders.OrderStatus

sealed class OrdersMainUiIntent {
    data object LoadMainOrders : OrdersMainUiIntent()
    data object CreateOrderMainList : OrdersMainUiIntent()
    data class GoToListOrders(val orderMainId: String) : OrdersMainUiIntent()
    data class DeleteMainList(val orderId: String) : OrdersMainUiIntent()
    data class UpdateStatusMainList(val orderId: String, val orderStatus: OrderStatus) :
        OrdersMainUiIntent()
    data object HideDialogs : OrdersMainUiIntent()
}

