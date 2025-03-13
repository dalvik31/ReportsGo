package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products


sealed class ProductsUiIntent {
    data object LoadProducts : ProductsUiIntent()
    data object AddProduct : ProductsUiIntent()
    data class DeleteProduct(val noteId: String) : ProductsUiIntent()
    data class EditNote(val noteId: String) : ProductsUiIntent()

}

