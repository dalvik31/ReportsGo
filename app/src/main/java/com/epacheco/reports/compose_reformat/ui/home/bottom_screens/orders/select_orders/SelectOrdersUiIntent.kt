package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.select_orders


sealed class SelectOrdersUiIntent {
    data object LoadSelectOrders : SelectOrdersUiIntent()
    data object HideDialogs : SelectOrdersUiIntent()
    data class SetOrderMainId(val orderMainId: String? = null) : SelectOrdersUiIntent()
    data object CreateOrderMain : SelectOrdersUiIntent()
}

