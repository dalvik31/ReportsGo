package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsSuccessDialog
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = hiltViewModel<OrdersViewModel>(),
    onBackPressed: (() -> Unit)? = null,
    mainOrderId: String,
    orderSeason: Season?
) {

    val uiState by ordersViewModel.uiState.collectAsState()
    val input by ordersViewModel.inputList.collectAsState()

    var showDialogCreateOrder by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        ordersViewModel.handleIntent(OrdersUiIntent.LoadOrders(mainOrderId))
    }

    LaunchedEffect(ordersViewModel) {
        ordersViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is OrdersUiEffect.NavigateToCreateOrder -> TODO()
            }
        }
    }

    OrdersView(
        orderList = uiState.orders,
        showImgEmptyList = uiState.showImgEmptyList,
        onBackPressed = { onBackPressed?.invoke() },
        onCreateOrderClick = {
            showDialogCreateOrder = true
        },
        onOrderClick = { Log.e("aqui", "estamooos clic a un pedido: ${it.nameOrder}") },
        isRefreshing = uiState.isLoading,
        onDeleteOrderClick = {
            ordersViewModel.handleIntent(OrdersUiIntent.DeleteOrder(it, mainOrderId))
        },
        onUpdateStatusOrderClick = {
            ordersViewModel.handleIntent(
                OrdersUiIntent.UpdateStatusOrder(
                    it.orderId,
                    mainOrderId,
                    orderStatus = it.orderStatus
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

    if (showDialogCreateOrder) {
        OrderInputDialog(input = input, onInputChanged = { e ->
            ordersViewModel.onValueInputListChanged(input = e)
        }, onDismissRequest = { showDialogCreateOrder = false }, onConfirmation = {
            showDialogCreateOrder = false
            ordersViewModel.handleIntent(OrdersUiIntent.CreateOrder(mainOrderId, orderSeason))
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