package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@OptIn(InternalCoilApi::class)
@Composable
fun ClientsScreen(
    clientsViewModel: ClientsViewModel = hiltViewModel<ClientsViewModel>(),
    onNavigateToClientDetail: (String?) -> Unit,
    onNavigateToCreateClient: (String?) -> Unit,
    onNavigateToProfile: (() -> Unit)? = null,
    isSelectableClient: Boolean = false,
    onClientSelected: ((String) -> Unit)? = null
) {
    val uiState by clientsViewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientsViewModel.handleIntent(ClientUiIntent.LoadClients())
        }
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                clientsViewModel.handleIntent(ClientUiIntent.Error())
            })
    }
    ClientsView(
        uiState.listClients,
        onNavigateToProfile = { onNavigateToProfile?.invoke() },
        onClientSelected = { client ->
            if (isSelectableClient) {
                onClientSelected?.invoke(client.id)
            } else {
                onNavigateToClientDetail.invoke(client.id)
            }

        },
        onNavigateToCreateClient = { idClient ->
            onNavigateToCreateClient.invoke(idClient)
        },
        isRefreshing = uiState.isLoading,
        onRefresh = {
            clientsViewModel.handleIntent(ClientUiIntent.LoadClients())
        },
        inputName = uiState.clientName,
        onInputNameChanged = {
            clientsViewModel.onInputNameChanged(it)
        }
    )
}

@Preview
@Composable
fun ClientsScreenPreview() {
    ReportsGoTheme {
        ClientsView()
    }
}