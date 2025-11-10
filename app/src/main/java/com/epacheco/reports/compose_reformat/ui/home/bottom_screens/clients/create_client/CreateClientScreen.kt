package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.create_client

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.utils.extensions.getContactDetails
import kotlinx.coroutines.flow.collectLatest

@OptIn(InternalCoilApi::class)
@Composable
fun CreateClientScreen(
    createClientViewModel: CreateClientViewModel = hiltViewModel<CreateClientViewModel>(),
    clientId: String? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    val uiState by createClientViewModel.uiState.collectAsState()


    var showDialogConfirmDeleteClient by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contactUri: Uri? = result.data?.data
                if (contactUri != null) {
                    val details = context.getContactDetails(contactUri)
                    createClientViewModel.onInputNameChanged(details.first ?: "")
                    createClientViewModel.onInputLastNameChanged(details.second ?: "")
                    createClientViewModel.onInputPhoneChanged(details.third ?: "")
                }
            }
        }
    )


    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientId?.let {
                createClientViewModel.handleIntent(CreateClientUiIntent.LoadClient(it))
            }
        }
    }

    LaunchedEffect(createClientViewModel) {
        createClientViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                CreateClientUiEffect.NavigateBack -> onBackPressed?.invoke()
            }
        }
    }

    CreateClientView(
        clientId = uiState.client?.id,
        inputName = uiState.clientName,
        onInputNameChanged = {
            createClientViewModel.onInputNameChanged(it)
        },
        inputLastName = uiState.clientLastName,
        onInputLastNameChanged = {
            createClientViewModel.onInputLastNameChanged(it)
        },
        inputInfo = uiState.clientInfo,
        onInputInfoChanged = { createClientViewModel.onInputInfoChanged(it) },
        inputPhone = uiState.clientPhone,
        onInputPhoneChanged = { createClientViewModel.onInputPhoneChanged(it) },
        inputCredit = uiState.clientCredit,
        onInputCreditChanged = { createClientViewModel.onInputCreditChanged(it) },
        onCreateClient = {
            createClientViewModel.handleIntent(CreateClientUiIntent.CreateClient)
        },
        onDeleteClient = {
            showDialogConfirmDeleteClient = true
        },
        onUpdateClient = {
            createClientViewModel.handleIntent(CreateClientUiIntent.UpdateClient(it))
        },
        onBackPressed = {
            onBackPressed?.invoke()
        },
        onSelectContact = {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type =
                    ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE // Or other types like ContactsContract.Contacts.CONTENT_TYPE
            }

            launcher.launch(intent)
        })

    if (uiState.isLoading) {
        Loader(false)
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            dialogTitle = stringResource(R.string.title_information),
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                createClientViewModel.handleIntent(CreateClientUiIntent.HideDialogs)
            })
    }

    uiState.successMessage?.let { msgSuccessOperation ->
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_ok,
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                createClientViewModel.handleIntent(CreateClientUiIntent.HideDialogs)
            })
    }


    if (showDialogConfirmDeleteClient) {
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_remove,
            dialogTitle = stringResource(R.string.msg_delete_main_order_title),
            dialogSubTitle = stringResource(
                R.string.confirm_delete_client
            ),
            confirmButtonText = stringResource(R.string.btn_ok_delete),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmDeleteClient = false },
            onConfirmation = {
                showDialogConfirmDeleteClient = false
                clientId?.let {
                    createClientViewModel.handleIntent(CreateClientUiIntent.DeleteClient(it))
                }
            }
        )
    }

}

