package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent


sealed class ProductsUiIntent {
    data object LoadProducts : ProductsUiIntent()
    data object Error : ProductsUiIntent()

}

