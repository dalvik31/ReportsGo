package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.edit_order

sealed class EditOrderUiEffect {
    data object NavigateBack : EditOrderUiEffect()
}
