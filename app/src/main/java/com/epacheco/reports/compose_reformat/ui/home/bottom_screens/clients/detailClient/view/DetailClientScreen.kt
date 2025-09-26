package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.viewModel.DetailClientViewModel

@Composable
fun DetailClientScreen(viewModel: DetailClientViewModel = hiltViewModel<DetailClientViewModel>(), clientId: String) {

    val clientUiState by viewModel.clientFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getClientDetail(clientId = clientId)
    }

    if (clientUiState.isLoading) {
        Loader(false)
    }

    clientUiState.errorMessage?.let { msgError ->
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                Log.e("aqui", "ClientsViewModel vamooos: ${msgError}")
            })
    }


    DetailClientView(client = clientUiState.clientDetail)

}