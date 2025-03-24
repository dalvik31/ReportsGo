package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main

sealed class OrdersMainUiEffect {
    data class NavigateToElementsMain(val orderParentId: String) : OrdersMainUiEffect()
}
