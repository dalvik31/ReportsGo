package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

sealed class NewOrderUiEffect {
    data object NavigateBack : NewOrderUiEffect()
}
