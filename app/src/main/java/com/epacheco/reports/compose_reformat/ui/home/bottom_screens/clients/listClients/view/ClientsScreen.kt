package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.viewModel.ClientsViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsUiIntent
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
    val clientsUiState by clientsViewModel.uiState.collectAsState()
    val inputName by clientsViewModel.inputClientName.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientsViewModel.handleIntent(ClientUiIntent.LoadClients(null))
        }
    }

    if (clientsUiState.isLoading) {
        Loader(false)
    }

    clientsUiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                clientsViewModel.handleIntent(ClientUiIntent.Error())
            })
    }
    ClientsView(
        clientsUiState.listClients,
        onNavigateToProfile = { onNavigateToProfile?.invoke() },
        onClientSelected = { client ->
            if (isSelectableClient) {
                onClientSelected?.invoke(client.id)
            } else {
                Toast.makeText(context, client.name, Toast.LENGTH_LONG).show()
                onNavigateToClientDetail.invoke(client.id)
            }

        },
        onNavigateToCreateClient = { idClient ->
            onNavigateToCreateClient.invoke(idClient)
        },
        inputName = inputName,
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