package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiEffect

sealed class ProductDetailUiEffect {
    data object NavigateBack : ProductDetailUiEffect()
}
