package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent


sealed class ProductsUiIntent {
    data object LoadProducts : ProductsUiIntent()
    data class Error(val msgError: String? = null) : ProductsUiIntent()

}

