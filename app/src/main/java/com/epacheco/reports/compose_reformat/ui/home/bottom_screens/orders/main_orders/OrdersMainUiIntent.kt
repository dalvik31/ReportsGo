package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders

import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season

sealed class OrdersMainUiIntent {
    data object LoadMainOrders : OrdersMainUiIntent()
    data object CreateOrderMainList : OrdersMainUiIntent()
    data class GoToListOrders(val orderMainId: String, val orderSeason: Season?) : OrdersMainUiIntent()
    data class DeleteMainList(val orderId: String) : OrdersMainUiIntent()
    data class UpdateStatusMainList(val orderId: String, val orderStatus: OrderStatus) :
        OrdersMainUiIntent()
    data object HideDialogs : OrdersMainUiIntent()
}

