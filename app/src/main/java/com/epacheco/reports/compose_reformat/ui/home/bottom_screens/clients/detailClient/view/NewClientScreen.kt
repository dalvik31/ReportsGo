package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
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
import com.epacheco.reports.compose_reformat.general_components.CheckPermission
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.viewModel.DetailClientViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product.ProductDetailUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product.ProductDetailUiIntent
import kotlinx.coroutines.flow.collectLatest

@OptIn(InternalCoilApi::class)
@Composable
fun NewClientScreen(
    detailClientViewModel: DetailClientViewModel = hiltViewModel<DetailClientViewModel>(),
    clientId: String? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    var showDialogConfirmDeleteClient by remember { mutableStateOf(false) }
    val uiState by detailClientViewModel.uiState.collectAsState()
    val inputNames by detailClientViewModel.inputClientNames.collectAsState()
    val inputLastName by detailClientViewModel.inputClientLastName.collectAsState()
    val inputInfo by detailClientViewModel.inputClientInfo.collectAsState()
    val inputPhone by detailClientViewModel.inputClientPhone.collectAsState()
    val inputCredit by detailClientViewModel.inputClientCredit.collectAsState()
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contactUri: Uri? = result.data?.data
                if (contactUri != null) {
                    val details = getContactDetails(context, contactUri)
                    detailClientViewModel.onInputNameChanged(details.first ?: "")
                    detailClientViewModel.onInputLastNameChanged(details.second ?: "")
                    detailClientViewModel.onInputPhoneChanged(details.third ?: "")
                }
            }
        }
    )


    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            clientId?.let {
                detailClientViewModel.handleIntent(
                    ClientDetailUiIntent.LoadClient(
                        it,
                        isEditMode = true
                    )
                )
            }
        }
    }

    LaunchedEffect(detailClientViewModel) {
        detailClientViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                ClientDetailUiEffect.NavigateBack -> onBackPressed?.invoke()
            }
        }
    }

    NewClientView(
        clientId = uiState.clientDetail?.id,
        inputName = inputNames,
        onInputNameChanged = {
            detailClientViewModel.onInputNameChanged(it)
        },
        inputLastName = inputLastName,
        onInputLastNameChanged = {
            detailClientViewModel.onInputLastNameChanged(it)
        },
        inputInfo = inputInfo,
        onInputInfoChanged = { detailClientViewModel.onInputInfoChanged(it) },
        inputPhone = inputPhone,
        onInputPhoneChanged = { detailClientViewModel.onInputPhoneChanged(it) },
        inputCredit = inputCredit,
        onInputCreditChanged = { detailClientViewModel.onInputCreditChanged(it) },
        onCreateClient = {
            detailClientViewModel.handleIntent(ClientDetailUiIntent.CreateClient)
        },
        onDeleteClient = {
            showDialogConfirmDeleteClient = true
        },
        onUpdateClient = {
            detailClientViewModel.handleIntent(ClientDetailUiIntent.UpdateClient(it))
        },
        onBackPressed = {
            onBackPressed?.invoke()
        },
        onSelectContact = {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE // Or other types like ContactsContract.Contacts.CONTENT_TYPE
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
                detailClientViewModel.handleIntent(ClientDetailUiIntent.HideDialogs)
            })
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
                    detailClientViewModel.handleIntent(ClientDetailUiIntent.DeleteClient(it))
                }
            }
        )
    }

}

fun getContactDetails(context: android.content.Context, contactUri: Uri): Triple<String?, String?, String?> {
    var name: String? = null
    var lastName: String? = null
    var phoneNo: String? = null

    val cursor: Cursor? = context.contentResolver.query(contactUri, null, null, null, null)

    try {
        if (cursor != null && cursor.moveToFirst()) {
            Log.e("aqui","vamoooos phone cursor: ${cursor.moveToFirst()}")
            val phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

            if (phoneIndex != -1) {
                phoneNo = cursor.getString(phoneIndex)
            }
            if (nameIndex != -1) {
                name = cursor.getString(nameIndex).substringBefore(" ")
                lastName = cursor.getString(nameIndex).substringAfterLast(" ")
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        cursor?.close()
    }
    return Triple(name, lastName, phoneNo)
}