package com.epacheco.reports.compose_reformat.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_HOME
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_LOGIN
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.getTranslateFireBaseErrorMsg


@Composable
fun LoginScreen(registerViewModel: RegisterViewModel?, navController: NavController) {
    val authResource = registerViewModel?.loginFlow?.collectAsState()
    authResource?.value?.let {
        when (it) {
            is Resource.Failure -> {
                if (it.exception != null) {
                    registerViewModel.goToRegisterFlow()
                    registerViewModel.showToastMsg(it.exception.message?.getTranslateFireBaseErrorMsg())
                } else {
                    RegisterScreen(registerViewModel, navController)
                }
            }

            is Resource.Loading -> Loader()

            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                }
            }
        }

    }
}


@Preview()
@Composable
fun ShowLoginScreenPreview() {
    ReportsGoTheme {
        LoginScreen(null, rememberNavController())
    }
}

