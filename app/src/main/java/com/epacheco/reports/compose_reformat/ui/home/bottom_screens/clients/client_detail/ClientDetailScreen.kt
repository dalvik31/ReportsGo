package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_detail

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.CheckPermission
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.utils.extensions.gotoApplicationContact

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
    var showPhoneDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientId?.let {
                detailClientViewModel.handleIntent(ClientDetailUiIntent.LoadClient(it))
            }
        }
    }

    ClientDetailView(
        client = uiState.client,
        onBackPressed = {
            onBackPressed?.invoke()
        },
        inputAmount = uiState.clientAmount,
        onInputAmountChanged = {
            detailClientViewModel.onInputAmountChanged(it)
        },
        inputConcept = uiState.clientConcept,
        onInputConceptChanged = { detailClientViewModel.onInputConceptChanged(it) },
        openClientTransaction = { openClientTransaction?.invoke(it) },
        openClientSale = { openClientSale?.invoke(it) },
        openClientOrder = { openClientOrder?.invoke(it) },
        onCreatePayment = {
            detailClientViewModel.handleIntent(
                ClientDetailUiIntent.UpdateAmountPayClient(it)
            )
        },
        onPhoneClick = {
            showPhoneDialog = true
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

    if (showPhoneDialog) {
        CheckPermission(
            permission = Manifest.permission.CALL_PHONE,
            iconPermission = R.drawable.ic_vector_phone,
            onGranted = {
                context.gotoApplicationContact(uiState.client?.phone)
                showPhoneDialog = false
            },
            permissionRationaleTitle = stringResource(R.string.permission_phone_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_phone_settings_title),
            onCancel = { showPhoneDialog = false }
        )

    }

}