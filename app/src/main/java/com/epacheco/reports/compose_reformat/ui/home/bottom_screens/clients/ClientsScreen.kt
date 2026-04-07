package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients

import android.Manifest
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
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.CheckPermission
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.gotoApplicationContact

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
    var showPhoneDialog by remember { mutableStateOf(false) }
    var clientPhone: String? = null
    val context = LocalContext.current

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
        },
        onPhoneClick = {
            showPhoneDialog = true
            clientPhone = it
        }
    )

    if (showPhoneDialog) {
        CheckPermission(
            permission = Manifest.permission.CALL_PHONE,
            iconPermission = R.drawable.ic_vector_phone,
            onGranted = {
                context.gotoApplicationContact(clientPhone)
                showPhoneDialog = false
            },
            permissionRationaleTitle = stringResource(R.string.permission_phone_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_phone_settings_title),
            onCancel = { showPhoneDialog = false }
        )

    }
}

@Preview
@Composable
fun ClientsScreenPreview() {
    ReportsGoTheme {
        ClientsView()
    }
}