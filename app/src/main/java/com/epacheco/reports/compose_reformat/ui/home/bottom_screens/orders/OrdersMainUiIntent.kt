package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import com.epacheco.reports.compose_reformat.model.orders.OrderStatus

sealed class OrdersMainUiIntent {
    data object LoadMainOrders : OrdersMainUiIntent()
    data class UpdateMainListStatus(val mainListId: String, val newOrderStatus: OrderStatus) : OrdersMainUiIntent()
    data class DeleteMainList(val orderId: String) : OrdersMainUiIntent()
    data object Error : OrdersMainUiIntent()  // Show error
}

