package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.orders

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.model.orders.Season

@OptIn(InternalCoilApi::class)
@Composable
fun ClientOrderScreen(
    clientOrdersViewModel: ClientOrdersViewModel = hiltViewModel<ClientOrdersViewModel>(),
    onOrderMainSelected: ((String, Season?, String, String?) -> Unit)? = null,
    clientId: String? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    val uiState by clientOrdersViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientOrdersViewModel.handleIntent(
                ClientOrdersUiIntent.LoadClientOrders
            )
        }
    }

    ClientOrdersView(clientOrders = uiState.orderMainList, onBackPressed = {
        onBackPressed?.invoke()
    }, onOrderSelected = {
        onOrderMainSelected?.invoke(it.orderId, it.orderSeason, it.nameOrder, clientId)
    })
    if (uiState.isLoading) {
        Loader(false)
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                clientOrdersViewModel.handleIntent(ClientOrdersUiIntent.HideDialogs)
            })
    }

}