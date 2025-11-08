package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import com.epacheco.reports.compose_reformat.model.products.Product


data class ProductsUiState(
    val listProducts: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)