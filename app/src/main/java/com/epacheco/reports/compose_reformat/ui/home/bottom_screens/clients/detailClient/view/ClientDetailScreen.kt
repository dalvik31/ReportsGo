package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

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
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.viewModel.DetailClientViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainUiIntent

@OptIn(InternalCoilApi::class)
@Composable
fun ClientDetailScreen(
    detailClientViewModel: DetailClientViewModel = hiltViewModel<DetailClientViewModel>(),
    clientId: String? = null,
    onBackPressed: (() -> Unit)? = null,
    openClientTransaction: ((String) -> Unit)? = null,
    openClientOrder: ((String) -> Unit)? = null,
    openClientSale: ((String) -> Unit)? = null,
) {

    val uiState by detailClientViewModel.uiState.collectAsState()
    val inputAmount by detailClientViewModel.inputClientAmount.collectAsState()
    val inputConcept by detailClientViewModel.inputClientConcept.collectAsState()

    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientId?.let {
                detailClientViewModel.handleIntent(
                    ClientDetailUiIntent.LoadClient(
                        it
                    )
                )
            }
        }
    }

    ClientDetailView(
        client = uiState.clientDetail,
        clientTransaction = uiState.clientTransactions,
        onBackPressed = {
            onBackPressed?.invoke()
        },
        inputAmount = inputAmount,
        onInputAmountChanged = {
            detailClientViewModel.onInputAmountChanged(it)
        },
        inputConcept = inputConcept,
        onInputConceptChanged = { detailClientViewModel.onInputConceptChanged(it) },
        openClientTransaction = { openClientTransaction?.invoke(it) },
        openClientSale = { openClientSale?.invoke(it) },
        openClientOrder = { openClientOrder?.invoke(it) },
        onCreatePayment = {
            detailClientViewModel.handleIntent(
                ClientDetailUiIntent.UpdateAmountPayClient(it)
            )
        })

    if (uiState.isLoading) {
        Loader(false)
    }
    uiState.successMessage?.let { msgSuccessOperation ->
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_ok,
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                detailClientViewModel.handleIntent(ClientDetailUiIntent.HideDialogs)
            })
    }


    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                detailClientViewModel.handleIntent(ClientDetailUiIntent.HideDialogs)
            })
    }


}