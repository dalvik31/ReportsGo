package com.epacheco.reports.compose_reformat.ui.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel = hiltViewModel<AccountViewModel>(),
    onNavigateToHome: () -> Unit,
    onNavigateToPassword: () -> Unit,
) {

    val email = accountViewModel.email.collectAsState()
    val password = accountViewModel.password.collectAsState()
    val enabledButtonContinue = accountViewModel.enabledLoginButton.collectAsState()
    val uiState by accountViewModel.uiState.collectAsState()

    LaunchedEffect(accountViewModel) {
        accountViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                AccountUiEffect.NavigateToHome -> onNavigateToHome.invoke()
                AccountUiEffect.NavigateToPassword -> onNavigateToPassword.invoke()
            }
        }
    }

    AccountView(
        email.value,
        password.value,
        enabledButtonContinue = enabledButtonContinue.value,
        onEmailChanged = { e: String, p: String ->
            accountViewModel.onValueLoginChanged(
                email = e,
                password = p
            )
        },
        onPasswordChanged = { e: String, p: String ->
            accountViewModel.onValueLoginChanged(email = e, password = p)
        },
        onLoginClicked = {
            accountViewModel.handleIntent(AccountUiIntent.SignIn)
        },
        onRegisterClicked = {
            accountViewModel.handleIntent(AccountUiIntent.SignUp)
        },
        onPasswordClicked = {
            accountViewModel.handleIntent(AccountUiIntent.ChangePassword)
        }
    )


    // Show loading
    if (uiState.isLoading) {
        Loader(false)
    }


    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_error,
            dialogSubTitle = msgError,
            confirmButtonText = stringResource(R.string.btn_ok),
            onConfirmation = {
                accountViewModel.handleIntent(AccountUiIntent.HideMsgError)
            })
    }

}

@Preview
@Composable
fun RegisterScreenPreview() {
    ReportsGoTheme {
        AccountView()
    }
}