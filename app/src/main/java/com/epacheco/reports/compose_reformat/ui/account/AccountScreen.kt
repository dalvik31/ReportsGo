package com.epacheco.reports.compose_reformat.ui.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    accountViewModel: AccountViewModel = hiltViewModel<AccountViewModel>(),
    onNavigateToHome: () -> Unit,
    onNavigateToPassword: () -> Unit,
) {
    val context = LocalContext.current
    val credentialManager = remember { CredentialManager.create(context) }
    val coroutineScope = rememberCoroutineScope()

    val googleId = stringResource(R.string.default_web_client_id)

    fun getCredential(onSignInSuccess: (getCredentialResponse: GetCredentialResponse) -> Unit) {
        coroutineScope.launch {
            try {
                val signInWithGoogleOption: GetSignInWithGoogleOption =
                    GetSignInWithGoogleOption.Builder(googleId)
                        .build()

                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(signInWithGoogleOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                onSignInSuccess.invoke(result)

            } catch (e: GetCredentialException) {
                e.printStackTrace()
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

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
        uiState.email,
        uiState.password,
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
        },
        onLoginGoogleClicked = {
            getCredential {
                accountViewModel.handleIntent(AccountUiIntent.GoogleSignIn(it))
            }
        }

    )

    if (uiState.isLoading) {
        Loader(false)
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            dialogSubTitle = msgError,
            confirmButtonText = stringResource(R.string.btn_ok),
            onDismissRequest = {
                accountViewModel.handleIntent(AccountUiIntent.HideMsgError)
            },
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