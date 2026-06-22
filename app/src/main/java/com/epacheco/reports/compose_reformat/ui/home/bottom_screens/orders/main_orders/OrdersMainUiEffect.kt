package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders

import com.epacheco.reports.compose_reformat.model.orders.Season

sealed class OrdersMainUiEffect {
    data class NavigateToElementsMain(
        val orderMainId: String,
        val orderSeason: Season?,
        val orderNameMain: String,
        val progressList: Float
    ) :
        OrdersMainUiEffect()
}
