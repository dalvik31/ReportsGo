package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

sealed class ProductsUiEffect {
    data class NavigateToProductDetail(val productId: String?) : ProductsUiEffect()

}
