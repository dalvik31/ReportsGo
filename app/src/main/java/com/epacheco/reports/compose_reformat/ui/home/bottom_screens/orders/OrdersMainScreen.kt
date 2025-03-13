package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.view.OrderMainView
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun OrdersMainScreen(
    ordersMainViewModel: OrdersMainViewModel = hiltViewModel<OrdersMainViewModel>()
) {
    val uiState by ordersMainViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        ordersMainViewModel.handleIntent(OrdersMainUiIntent.LoadMainOrders)
    }

    LaunchedEffect(ordersMainViewModel) {
        ordersMainViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is OrdersMainUiEffect.NavigateToCreateMainList -> {}
                is OrdersMainUiEffect.NavigateToElementsMain -> {}
            }
        }
    }

    OrderMainView(
        orderMainList = uiState.orders,
        showImgEmptyList = uiState.showImgEmptyList,
        onMainOrderClick = { Log.e("aqui", "vamoooos a editar la lista:${it.nameOrder}") },
        onDeleteOrderClick = {
            ordersMainViewModel.handleIntent(OrdersMainUiIntent.DeleteMainList(it))
        })

    // Loading Overlay
    if (uiState.isLoading) {
        Loader(false)
    }

    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsErrorDialog(
            dialogSubTitle = msgError,
            onConfirmation = {
                ordersMainViewModel.handleIntent(OrdersMainUiIntent.Error)
            })
    }

}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    ReportsGoTheme {
        OrderMainView(onMainOrderClick = {}, onDeleteOrderClick = {})
    }

}
