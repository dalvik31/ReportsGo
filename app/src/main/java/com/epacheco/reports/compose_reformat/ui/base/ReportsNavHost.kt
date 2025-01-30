package com.epacheco.reports.compose_reformat.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.home.HomeScreen
import com.epacheco.reports.compose_reformat.ui.login.RegisterScreen
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_HOME
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_PASSWORD
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_REGISTER
import com.epacheco.reports.compose_reformat.ui.recovery_password.RecoveryPassword
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun ReportsNavHost(
    registerViewModel: RegisterViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    ReportsGoTheme {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = if (registerViewModel.isUserLogin()) ROUTE_HOME else ROUTE_REGISTER
        ) {
            composable(ROUTE_REGISTER) {
                RegisterScreen(registerViewModel, navController)
            }
            composable(ROUTE_PASSWORD) {
                RecoveryPassword(registerViewModel, navController)
            }
            composable(ROUTE_HOME) {
                HomeScreen(registerViewModel, navController)
            }
        }

    }


}