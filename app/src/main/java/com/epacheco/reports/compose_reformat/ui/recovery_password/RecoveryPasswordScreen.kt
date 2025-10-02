package com.epacheco.reports.compose_reformat.ui.recovery_password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun PasswordScreen(
    recoverPasswordViewModel: RecoverPasswordViewModel = hiltViewModel<RecoverPasswordViewModel>(),
    onBackPressed: (() -> Unit)? = null,
) {
    val uiState by recoverPasswordViewModel.uiState.collectAsState()
    val inputEmail by recoverPasswordViewModel.inputEmail.collectAsState()
    val inputEmailIsValid by recoverPasswordViewModel.enabledButton.collectAsState()

    RecoveryPasswordView(
        inputEmail = inputEmail,
        onInputEmailChanged = {
            recoverPasswordViewModel.onInputEmailChanged(it)
        },
        inputEmailIsValid = inputEmailIsValid,
        onBackPressed = {
            onBackPressed?.invoke()
        }, onSendEmail = {
            recoverPasswordViewModel.handleIntent(RecoveryPasswordUiIntent.RecoveryPassword)
        })

    // Loading Overlay
    if (uiState.isLoading) {
        Loader(false)
    }


    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                recoverPasswordViewModel.handleIntent(RecoveryPasswordUiIntent.HideDialogs)
            })
    }

    //Message success
    uiState.successOperationMsg?.let { msgSuccessOperation ->
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_ok,
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                recoverPasswordViewModel.handleIntent(RecoveryPasswordUiIntent.HideDialogs)
                onBackPressed?.invoke()
            })
    }
}

@Preview()
@Composable
fun PasswordScreenPreview() {
    ReportsGoTheme {
        RecoveryPasswordViewPreview()
    }
}
