package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun ProductsScreen(
    ordersViewModel: ProductsViewModel = hiltViewModel<ProductsViewModel>(),
    onNavigateToProductDetail: ((String?) -> Unit)? = null,
    onNavigateToProfile: (() -> Unit)? = null,
    isSelectableProduct: Boolean = false,
    onProductSelected: ((String) -> Unit)? = null
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val currentState = lifecycleOwner.lifecycle.currentState
    val inputName by ordersViewModel.inputProductName.collectAsState()
    val uiState by ordersViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            ordersViewModel.handleIntent(ProductsUiIntent.LoadProducts)
        }
    }


    ProductsView(
        productList = uiState.listProducts,
        inputName = inputName,
        onInputNameChanged = {
            ordersViewModel.onInputNameChanged(it)
        },
        isRefreshing = uiState.isLoading,
        onRefresh = {
            ordersViewModel.handleIntent(ProductsUiIntent.LoadProducts)
        }, onNavigateToProfile = {
            onNavigateToProfile?.invoke()
        }, onGoProductDetailClick = { product, inStock ->
            if (isSelectableProduct) {
                product?.let {
                    if ((inStock ?: 0) > 0) {
                        onProductSelected?.invoke(product)
                    } else {
                        ordersViewModel.handleIntent(ProductsUiIntent.Error("Sin inventario, selecciona otro producto"))
                    }

                } ?: run {
                    onNavigateToProductDetail?.invoke(null)
                }

            } else {
                onNavigateToProductDetail?.invoke(product)
            }

        })

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            dialogTitle = stringResource(R.string.title_information),
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                ordersViewModel.handleIntent(ProductsUiIntent.Error())
            })
    }

}


@Preview
@Composable
fun ProductsScreenPreview() {
    ReportsGoTheme {
        ProductsView()
    }

}