package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import java.io.File


sealed class ProductDetailUiIntent {
    data class LoadProduct(val productId: String) : ProductDetailUiIntent()
    data class SetImageFile(val imgFile: File) : ProductDetailUiIntent()
    data class UpdateProduct(val productId: String)  : ProductDetailUiIntent()
    data object CreateProduct : ProductDetailUiIntent()
    data class DeleteProduct(val productId: String) : ProductDetailUiIntent()
    data object Error : ProductDetailUiIntent()
    data object HideDialogs : ProductDetailUiIntent()

}

