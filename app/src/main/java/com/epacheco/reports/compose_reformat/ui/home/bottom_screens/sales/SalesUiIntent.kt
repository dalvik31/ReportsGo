package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import android.graphics.Bitmap
import android.net.Uri
import com.epacheco.reports.compose_reformat.model.products.Product
import java.io.File

sealed class SalesUiIntent {
    data object Error : SalesUiIntent()
    data class GetClientById(val clientId: String?) : SalesUiIntent()
    data class GetProductById(val productId: String?) : SalesUiIntent()
    data class UpdateStock(val product: Product, val incrementValue: Boolean) : SalesUiIntent()
    data class RemoveProductList(val productId: String?) : SalesUiIntent()

}

