package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

sealed class OrdersMainUiEffect {
    data class NavigateToElementsMain(val orderParentId: String) : OrdersMainUiEffect()
    data object NavigateToCreateMainList : OrdersMainUiEffect()
}
