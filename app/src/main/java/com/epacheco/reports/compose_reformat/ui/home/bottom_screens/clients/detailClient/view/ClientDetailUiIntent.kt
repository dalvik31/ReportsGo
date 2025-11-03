package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import java.io.File


sealed class ClientDetailUiIntent {
    data class LoadClient(val clientId: String, val isEditMode: Boolean = false) :
        ClientDetailUiIntent()
    data class UpdateClient(val clientId: String) : ClientDetailUiIntent()
    data class UpdateAmountPayClient(val clientId: String) : ClientDetailUiIntent()
    data object CreateClient : ClientDetailUiIntent()
    data class DeleteClient(val clientId: String) : ClientDetailUiIntent()
    data object HideDialogs : ClientDetailUiIntent()

}

