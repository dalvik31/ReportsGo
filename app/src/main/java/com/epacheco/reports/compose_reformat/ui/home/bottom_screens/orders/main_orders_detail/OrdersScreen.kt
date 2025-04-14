package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsSuccessDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = hiltViewModel<OrdersViewModel>(),
    onBackPressed: (() -> Unit)? = null,
    onNavigateToCreateOrder: ((String, Season?) -> Unit)? = null,
    onNavigateToEditOrder: ((Order) -> Unit)? = null,
    mainOrderId: String,
    orderSeason: Season?
) {
    val uiState by ordersViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        ordersViewModel.handleIntent(OrdersUiIntent.LoadOrders(mainOrderId))
    }

    LaunchedEffect(ordersViewModel) {
        ordersViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is OrdersUiEffect.NavigateToCreateOrder -> onNavigateToCreateOrder?.invoke(
                    mainOrderId,
                    orderSeason
                )

            }
        }
    }

    OrdersView(
        orderList = uiState.orders,
        showImgEmptyList = uiState.showImgEmptyList,
        onBackPressed = { onBackPressed?.invoke() },
        onCreateOrderClick = {
            onNavigateToCreateOrder?.invoke(mainOrderId, orderSeason)
        },
        onOrderClick = {
            onNavigateToEditOrder?.invoke(it)
        },
        isRefreshing = uiState.isLoading,
        onDeleteOrderClick = {
            ordersViewModel.handleIntent(OrdersUiIntent.DeleteOrder(it, mainOrderId))
        },
        onUpdateStatusOrderClick = {
            ordersViewModel.handleIntent(
                OrdersUiIntent.UpdateStatusOrder(
                    it.orderId,
                    mainOrderId,
                    orderBuy = !it.orderBuy
                )
            )
        },
        onRefresh = {
            ordersViewModel.handleIntent(OrdersUiIntent.LoadOrders(mainOrderId))
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
                ordersViewModel.handleIntent(OrdersUiIntent.HideDialogs)
            })
    }

    //Message success
    uiState.successOperationMsg?.let { msgSuccessOperation ->
        ReportsSuccessDialog(
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                ordersViewModel.handleIntent(OrdersUiIntent.HideDialogs)
            })
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrdersScreenPreview() {
    ReportsGoTheme {
        OrdersView()
    }

}