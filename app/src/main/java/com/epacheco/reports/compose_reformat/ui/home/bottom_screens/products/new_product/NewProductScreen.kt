package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun NewProductScreen(
    newProductViewModel: NewProductViewModel = hiltViewModel<NewProductViewModel>(),
    productToEdit: String? = null,
) {

    val uiState by newProductViewModel.uiState.collectAsState()

    NewProductView(productToEdit = productToEdit)

    // Loading Overlay
    if (uiState.isLoading) {
        Loader(false)
    }

    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsErrorDialog(
            dialogSubTitle = msgError,
            onConfirmation = {
                newProductViewModel.handleIntent(ProductsUiIntent.Error)
            })
    }

}


@Preview
@Composable
fun ProductsScreenPreview() {
    ReportsGoTheme {
        NewProductScreen()
    }

}