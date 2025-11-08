package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

sealed class OrdersUiEffect {
    data class NavigateToCreateOrder(val orderMainId: String) : OrdersUiEffect()
}
