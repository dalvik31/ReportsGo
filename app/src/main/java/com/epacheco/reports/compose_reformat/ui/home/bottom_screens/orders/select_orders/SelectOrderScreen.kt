package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.select_orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsInfoDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.utils.SeasonUtils

@OptIn(InternalCoilApi::class)
@Composable
fun SelectOrderScreen(
    selectOrdersViewModel: SelectOrdersViewModel = hiltViewModel<SelectOrdersViewModel>(),
    onOrderMainSelected: ((String, Season?, String, String?) -> Unit)? = null,
    clientId: String? = null,
    orderListSelected: List<Order>? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    val uiState by selectOrdersViewModel.uiState.collectAsState()
    var showInfoDialog by remember { mutableStateOf(false) }

    var showInfoMoveOrdersDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            selectOrdersViewModel.handleIntent(
                SelectOrdersUiIntent.LoadSelectOrders
            )
        }
    }

    SelectOrderView(
        clientOrders = uiState.orderMainList, onBackPressed = {
            onBackPressed?.invoke()
        }, onOrderSelected = { order ->
            clientId?.let {
                onOrderMainSelected?.invoke(order.orderId, order.orderSeason, order.nameOrder, it)
            } ?: run {
                showInfoMoveOrdersDialog = true
            }

        },
        onCreateOrderClick = {
            showInfoDialog = true
        },
        selectOrderMode = clientId.isNullOrEmpty() && !orderListSelected.isNullOrEmpty()
    )
    if (uiState.isLoading) {
        Loader(false)
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                selectOrdersViewModel.handleIntent(SelectOrdersUiIntent.HideDialogs)
            })
    }

    if (showInfoDialog) {
        val currentSeason = SeasonUtils.getSeason()
        ReportsInfoDialog(
            dialogTitle = stringResource(R.string.title_season),
            lottieAnimation = when (currentSeason) {
                Season.FALL -> R.raw.shopping_fall
                Season.SPRING -> R.raw.shopping_spring
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
                selectOrdersViewModel.handleIntent(SelectOrdersUiIntent.CreateOrderMain)
            },
            confirmButtonText = stringResource(R.string.btn_create_order_list),
            iconDialog = when (currentSeason) {
                Season.FALL -> R.drawable.ic_snow
                Season.SPRING -> R.drawable.ic_sun
            },
        )
    }

    if (showInfoMoveOrdersDialog) {
        ReportsInfoDialog(
            dialogTitle = stringResource(R.string.option_info),
            background = White,
            dialogSubTitle = "¿Estas seguro de mover los pedidos ${orderListSelected?.size} a otra lista?",
            onDismissRequest = {
                showInfoMoveOrdersDialog = false
            },
            onConfirmation = {
                showInfoMoveOrdersDialog = false
                //clientOrdersViewModel.handleIntent(ClientOrdersUiIntent.CreateOrderMain)
            },
            confirmButtonText = stringResource(R.string.move),
        )
    }
}

