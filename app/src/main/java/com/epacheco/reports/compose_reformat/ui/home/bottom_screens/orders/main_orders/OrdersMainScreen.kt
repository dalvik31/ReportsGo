package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsInfoDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsInputDialog
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.FallColor
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.SpringColor
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.SeasonUtils
import kotlinx.coroutines.flow.collectLatest


@Composable
fun OrdersMainScreen(
    ordersMainViewModel: OrdersMainViewModel = hiltViewModel<OrdersMainViewModel>(),
    onNavigateToElementsMain

    : ((String, Season?, String) -> Unit)? = null,
    onNavigateToProfile: (() -> Unit)? = null,
    clientId: String? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentState = lifecycleOwner.lifecycle.currentState
    val uiState by ordersMainViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showSelectMainOrder by remember { mutableStateOf(false) }
    var showDialogCreateOrder by remember { mutableStateOf(false) }

    var showInfoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.CREATED)) {
            ordersMainViewModel.handleIntent(OrdersMainUiIntent.LoadMainOrders)
        }

        clientId?.let {
            showSelectMainOrder = true
        }
    }

    LaunchedEffect(ordersMainViewModel) {
        ordersMainViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                is OrdersMainUiEffect.NavigateToElementsMain -> {
                    onNavigateToElementsMain?.invoke(
                        effect.orderMainId,
                        effect.orderSeason,
                        effect.orderNameMain,
                    )
                }
            }
        }
    }

    OrderMainView(
        orderMainMainList = uiState.orderMains,
        onNavigateToProfile = { onNavigateToProfile?.invoke() },
        onOrderClick = {
            ordersMainViewModel.handleIntent(
                OrdersMainUiIntent.GoToListOrders(
                    it.orderId.ifEmpty { it.dateOrder },
                    it.orderSeason,
                    it.nameOrder,
                    it.geProgressList()
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
                    it.orderId.ifEmpty { it.dateOrder },
                    orderStatus = it.orderStatus
                )
            )
        },
        onRefresh = {
            ordersMainViewModel.handleIntent(OrdersMainUiIntent.LoadMainOrders)
        })

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                ordersMainViewModel.handleIntent(OrdersMainUiIntent.HideDialogs)
            })
    }

    uiState.successOperationMsg?.let { msgSuccessOperation ->
        Toast.makeText(context, stringResource(msgSuccessOperation), Toast.LENGTH_SHORT).show()
        ordersMainViewModel.handleIntent(OrdersMainUiIntent.HideDialogs)
    }


    if (showInfoDialog) {
        val currentSeason = SeasonUtils.getSeason()
        ReportsInfoDialog(
            dialogTitle = stringResource(R.string.title_season),
            imgDialog = when (currentSeason) {
                Season.FALL -> R.drawable.ic_fashion_fall
                Season.SPRING -> R.drawable.ic_fashion_spring
            },
            background = White,
            dialogSubTitle = when (currentSeason) {
                Season.FALL -> stringResource(R.string.season_fall)
                Season.SPRING -> stringResource(R.string.season_spring)
            },
            onDismissRequest = {
                showInfoDialog = false
            },
            onConfirmation = {
                showInfoDialog = false
                ordersMainViewModel.handleIntent(OrdersMainUiIntent.CreateOrderMainList)
            },
            onInputChanged = {
                ordersMainViewModel.onValueInputListChanged(input = it)
            },
            confirmButtonText = stringResource(R.string.btn_create_order_list),
            iconDialog = R.drawable.ic_simple_dot,
            iconDialogTint = when (currentSeason) {
                Season.FALL -> FallColor
                Season.SPRING -> SpringColor
            }
        )
    }

    if (showSelectMainOrder) {
        ReportsInfoDialog(
            dialogTitle = stringResource(R.string.title_information),
            dialogSubTitle = "Selecciona una lista de pedidos",
            onDismissRequest = {
                showInfoDialog = false
            },
            onConfirmation = {
                showInfoDialog = false
                showDialogCreateOrder = true
            },
            confirmButtonText = stringResource(R.string.btn_understood)
        )
    }
    /* if (showDialogCreateOrder) {
         ReportsInputDialog(
             icon = R.drawable.ic_vector_order,
             text = uiState.listName,
             onInputChanged = { e ->
                 ordersMainViewModel.onValueInputListChanged(input = e)
             },
             confirmButtonText = stringResource(R.string.btn_create_order_list),
             dialogHint = stringResource(R.string.title_create_order_list),
             onDismissRequest = { showDialogCreateOrder = false },
             onConfirmation = {
                 showDialogCreateOrder = false
                 ordersMainViewModel.handleIntent(OrdersMainUiIntent.CreateOrderMainList)
             })
     }*/

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    ReportsGoTheme {
        OrderMainView(onOrderClick = {}, onDeleteOrderClick = {})
    }

}
