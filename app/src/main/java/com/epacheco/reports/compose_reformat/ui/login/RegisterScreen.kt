package com.epacheco.reports.compose_reformat.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ErrorAlertDialog
import com.epacheco.reports.compose_reformat.ui.navigation.NavHostScreens
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.getTranslateFireBaseErrorMsg

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel<RegisterViewModel>(),
    navController: NavController
) {
    val email = registerViewModel.email.collectAsState()
    val password = registerViewModel.password.collectAsState()
    val enabledButtonContinue = registerViewModel.enabledLoginButton.collectAsState()
    val authResource = registerViewModel.loginFlow.collectAsState()
    val msgError = registerViewModel.msgErrorFlow.collectAsState()

    msgError.value?.let { error ->
        ErrorAlertDialog(
            dialogSubTitle = error,
            onConfirmation = {
                registerViewModel.setMsgError(null)
            })

    }

    authResource.value.let { firebaseAuthResponse ->
        when (firebaseAuthResponse) {
            is Resource.Failure -> {
                /**
                 * Login fail by email or password
                 * */
                registerViewModel.setMsgError(firebaseAuthResponse.exception?.message?.getTranslateFireBaseErrorMsg()
                    ?.let { stringResource(it) })
                registerViewModel.goToRegisterFlow()
            }

            is Resource.Loading -> {
                /**
                 * Show loading
                 * */
                Loader(false, msgLoader = stringResource(R.string.loading))
            }

            is Resource.Success -> {
                /**
                 * Login success
                 * */
                LaunchedEffect(Unit) {
                    navController.navigate(NavHostScreens.HOME.route) {
                        popUpTo(NavHostScreens.REGISTER.route) { inclusive = true }
                    }
                }
            }

            null -> {
                /**
                 * User not found
                 * */
                RegisterScreenContent(
                    email.value,
                    password.value,
                    enabledButtonContinue = enabledButtonContinue.value,
                    navController,
                    onEmailChanged = { email: String, password: String ->
                        registerViewModel.onValueLoginChanged(
                            email,
                            password
                        )
                    },
                    onPasswordChanged = { email: String, password: String ->
                        registerViewModel.onValueLoginChanged(email, password)
                    },
                    onLoginClicked = {
                        registerViewModel.loginWithEmail()
                    },
                    onRegisterClicked = {
                        registerViewModel.createAccount()
                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    ReportsGoTheme {
        RegisterScreenContent()
    }
}