package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

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
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.ClientDetailUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.ClientDetailView
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.viewModel.DetailClientViewModel

@OptIn(InternalCoilApi::class)
@Composable
fun ClientInfoScreen(
    clientInfoViewModel: ClientInfoViewModel = hiltViewModel<ClientInfoViewModel>(),
    clientId: String? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    val uiState by clientInfoViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientId?.let {
                clientInfoViewModel.handleIntent(
                    ClientInfoUiIntent.LoadTransactions(
                        it
                    )
                )
            }
        }
    }

    ClientInfoView(
        clientTransaction = uiState.clientTransactions,
        isRefreshing = uiState.isLoading,
        onRefresh = {
            clientId?.let {
                clientInfoViewModel.handleIntent(
                    ClientInfoUiIntent.LoadTransactions(
                        it
                    )
                )
            }
        },
        onBackPressed = {
            onBackPressed?.invoke()
        })

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                clientInfoViewModel.handleIntent(ClientInfoUiIntent.HideDialogs)
            })
    }

}