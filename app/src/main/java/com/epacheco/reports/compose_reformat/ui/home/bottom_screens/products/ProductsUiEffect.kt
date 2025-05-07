package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

sealed class ProductsUiEffect {
    data class NavigateToEditProduct(val productId: String) : ProductsUiEffect()
    data object NavigateToAddProduct : ProductsUiEffect()
}
