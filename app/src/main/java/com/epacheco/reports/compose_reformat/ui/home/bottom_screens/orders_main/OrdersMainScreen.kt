package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main

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
import com.epacheco.reports.compose_reformat.general_components.dialogs.OrderInputDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsSuccessDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main.view.OrderMainView
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun OrdersMainScreen(
    ordersMainViewModel: OrdersMainViewModel = hiltViewModel<OrdersMainViewModel>(),
    onNavigateToElementsMain: ((String) -> Unit)? = null,
) {
    val uiState by ordersMainViewModel.uiState.collectAsState()
    val input by ordersMainViewModel.inputList.collectAsState()

    var showDialogCreateOrder by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        ordersMainViewModel.handleIntent(OrdersMainUiIntent.LoadMainOrders)
    }

    LaunchedEffect(ordersMainViewModel) {
        ordersMainViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is OrdersMainUiEffect.NavigateToElementsMain -> {

                    onNavigateToElementsMain?.invoke(effect.orderParentId)
                }
            }
        }
    }

    OrderMainView(
        orderMainList = uiState.orders,
        showImgEmptyList = uiState.showImgEmptyList,
        onMainOrderClick = { ordersMainViewModel.handleIntent(OrdersMainUiIntent.GoToListOrders(it.orderId)) },
        isRefreshing = uiState.isLoading,
        onCreateOrderMainClick = {
            showDialogCreateOrder = true
        },
        onDeleteOrderClick = {
            ordersMainViewModel.handleIntent(OrdersMainUiIntent.DeleteMainList(it))
        },
        onUpdateStatusOrderClick = {
            ordersMainViewModel.handleIntent(
                OrdersMainUiIntent.UpdateStatusMainList(
                    it.orderId,
                    orderStatus = it.orderStatus
                )
            )
        },
        onRefresh = {
            ordersMainViewModel.handleIntent(OrdersMainUiIntent.LoadMainOrders)
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
                ordersMainViewModel.handleIntent(OrdersMainUiIntent.HideDialogs)
            })
    }

    //Message success
    uiState.successOperationMsg?.let { msgSuccessOperation ->
        ReportsSuccessDialog(
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                ordersMainViewModel.handleIntent(OrdersMainUiIntent.HideDialogs)
            })
    }

    if (showDialogCreateOrder) {
        OrderInputDialog(input = input, onInputChanged = { e ->
            ordersMainViewModel.onValueInputListChanged(input = e)
        }, onDismissRequest = { showDialogCreateOrder = false }, onConfirmation = {
            showDialogCreateOrder = false
            ordersMainViewModel.handleIntent(OrdersMainUiIntent.CreateOrderMainList)
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
