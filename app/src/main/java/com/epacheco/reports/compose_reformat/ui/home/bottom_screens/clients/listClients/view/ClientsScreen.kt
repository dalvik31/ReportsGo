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
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.viewModel.ClientsViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun ClientsScreen(
    clientsViewModel: ClientsViewModel = hiltViewModel<ClientsViewModel>(),
    onNavigateToClientDetail : (String) -> Unit
) {
    val clientsUiState by clientsViewModel.clientsFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        clientsViewModel.getClients()
    }

    if (clientsUiState.isLoading) {
        Loader(false)
    }

    clientsUiState.errorMessage?.let { msgError ->
        ReportsAlertDialog (
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                Log.e("aqui", "ClientsViewModel vamooos: ${msgError}")
            })
    }
     ClientsView(clientsUiState.listClients){ client ->
         Toast.makeText(context, client.name, Toast.LENGTH_LONG).show()
         onNavigateToClientDetail.invoke(client.id)
     }
}

@Preview
@Composable
fun ClientsScreenPreview() {
    ReportsGoTheme {
        ClientsView()
    }
}