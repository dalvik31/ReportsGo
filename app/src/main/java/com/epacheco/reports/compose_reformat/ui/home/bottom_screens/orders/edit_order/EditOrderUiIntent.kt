package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.edit_order

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season

sealed class EditOrderUiIntent {
    data class CreateOrder(val mainOrderId: String, val orderSeason: Season?) : EditOrderUiIntent()
    data class DeleteOrder(val orderId: String, val mainOrderId: String) : EditOrderUiIntent()
    data class UpdateOrder(val order: Order) : EditOrderUiIntent()
    data object HideDialogs : EditOrderUiIntent()
}

