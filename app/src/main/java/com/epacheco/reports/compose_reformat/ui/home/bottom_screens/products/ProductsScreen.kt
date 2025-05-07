package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProductsScreen(
    ordersViewModel: ProductsViewModel = hiltViewModel<ProductsViewModel>()
) {

    val uiState by ordersViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        ordersViewModel.handleIntent(ProductsUiIntent.LoadProducts)
    }
    LaunchedEffect(ordersViewModel) {
        ordersViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                ProductsUiEffect.NavigateToAddProduct -> {

                }

                is ProductsUiEffect.NavigateToEditProduct -> {

                }
            }
        }
    }

    ProductsView(productList = uiState.listProducts, isRefreshing = uiState.isLoading, onRefresh = {
        ordersViewModel.handleIntent(ProductsUiIntent.LoadProducts)
    }) {
        Log.e("aqui", "vamoooos: ${it.productName}")
    }

    // Loading Overlay
    if (uiState.isLoading) {
        Loader(false)
    }

    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsErrorDialog(
            dialogSubTitle = msgError,
            onConfirmation = {
                ordersViewModel.handleIntent(ProductsUiIntent.Error)
            })
    }

}


@Preview
@Composable
fun ProductsScreenPreview() {
    ReportsGoTheme {
        ProductsView {}
    }

}