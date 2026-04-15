package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import com.epacheco.reports.compose_reformat.model.orders.Order

sealed class OrdersUiIntent {
    data class LoadOrders(val mainOrderId: String) : OrdersUiIntent()
    data class DeleteOrder(val orderId: String, val mainOrderId: String) : OrdersUiIntent()
    data class UpdateStatusOrder(
        val orderId: String,
        val mainOrderId: String,
        val orderBuy: Boolean,
        val locationLat: Double? = null,
        val locationLong: Double? = null,
        val address: String? = null
    ) :
        OrdersUiIntent()

    data class SetOrderSelected(val orderSelected: Order?) : OrdersUiIntent()
    data object HideDialogs : OrdersUiIntent()
}

