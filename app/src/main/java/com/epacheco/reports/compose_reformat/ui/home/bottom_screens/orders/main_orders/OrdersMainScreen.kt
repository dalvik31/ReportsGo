package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders

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
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsInfoDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsSuccessDialog
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.view.OrderMainInputDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.view.OrderMainView
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.SeasonUtils
import kotlinx.coroutines.flow.collectLatest


@Composable
fun OrdersMainScreen(
    ordersMainViewModel: OrdersMainViewModel = hiltViewModel<OrdersMainViewModel>(),
    onNavigateToElementsMain: ((String, Season?) -> Unit)? = null,
) {
    val uiState by ordersMainViewModel.uiState.collectAsState()
    val input by ordersMainViewModel.inputList.collectAsState()

    var showDialogCreateOrder by remember { mutableStateOf(false) }

    var showInfoDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        ordersMainViewModel.handleIntent(OrdersMainUiIntent.LoadMainOrders)
    }

    LaunchedEffect(ordersMainViewModel) {
        ordersMainViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is OrdersMainUiEffect.NavigateToElementsMain -> {
                    onNavigateToElementsMain?.invoke(effect.orderMainId, effect.orderSeason)
                }
            }
        }
    }

    OrderMainView(
        orderMainMainList = uiState.orderMains,
        showImgEmptyList = uiState.showImgEmptyList,
        onOrderClick = {
            ordersMainViewModel.handleIntent(
                OrdersMainUiIntent.GoToListOrders(
                    it.orderId,
                    it.orderSeason,
                )
            )
        },
        isRefreshing = uiState.isLoading,
        onCreateOrderMainClick = {
            showInfoDialog = true
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

    if (showInfoDialog) {

        val currentSeason = SeasonUtils.getSeason()
        ReportsInfoDialog(
            dialogTitle = stringResource(R.string.season_title),
            lottieAnimation = when (currentSeason) {
                Season.FALL -> R.raw.fall
                Season.SPRING -> R.raw.spring
            },
            dialogSubTitle = stringResource(
                R.string.msg_current_season, when (currentSeason) {
                    Season.FALL -> stringResource(R.string.season_fall)
                    Season.SPRING -> stringResource(R.string.season_spring)
                }
            ),
            onDismissRequest = {
                showInfoDialog = false
                showDialogCreateOrder = true
            },
            onConfirmation = {
                showInfoDialog = false
                showDialogCreateOrder = true
            },
            confirmButtonText = stringResource(R.string.btn_understood)
        )
    }
    if (showDialogCreateOrder) {
        OrderMainInputDialog(input = input, onInputChanged = { e ->
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
        OrderMainView(onOrderClick = {}, onDeleteOrderClick = {})
    }

}
