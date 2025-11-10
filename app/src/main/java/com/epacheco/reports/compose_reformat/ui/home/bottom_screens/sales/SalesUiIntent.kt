package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import com.epacheco.reports.compose_reformat.model.products.Product

sealed class SalesUiIntent {
    data object Error : SalesUiIntent()
    data class SaveSale(val isCreditSale: Boolean = false) : SalesUiIntent()
    data object RemoveClient : SalesUiIntent()
    data class GetClientById(val clientId: String?) : SalesUiIntent()
    data class GetProductById(val productId: String?) : SalesUiIntent()
    data class UpdateStock(val product: Product, val incrementValue: Boolean) : SalesUiIntent()
    data class RemoveProductList(val productId: String?) : SalesUiIntent()
    data class IsCreditSale(val isCreditSale: Boolean) : SalesUiIntent()
    data class SetNewLimit(val newLimit: Double) : SalesUiIntent()
    data class SetNewLimitUsed(val newLimitUsed: Double) : SalesUiIntent()
    data object HideDialogs : SalesUiIntent()

}

