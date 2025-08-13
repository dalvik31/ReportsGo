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
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProductsScreen(
    ordersViewModel: ProductsViewModel = hiltViewModel<ProductsViewModel>(),
    onNavigateToProductDetail: ((String?) -> Unit)? = null,
) {

    val inputName by ordersViewModel.inputProductName.collectAsState()
    val uiState by ordersViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        ordersViewModel.handleIntent(ProductsUiIntent.LoadProducts)
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
        }) {
        onNavigateToProductDetail?.invoke(it)
        Log.e("aqui", "vamoooos: ${it}")
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