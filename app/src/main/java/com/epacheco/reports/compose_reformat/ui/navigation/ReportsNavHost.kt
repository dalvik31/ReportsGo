package com.epacheco.reports.compose_reformat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.home.HomeScreen
import com.epacheco.reports.compose_reformat.ui.login.LoginScreen
import com.epacheco.reports.compose_reformat.ui.recovery_password.RecoveryPassword
import com.epacheco.reports.compose_reformat.ui.login.RegisterScreen
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel

@Composable
fun AppNavHost(
    registerViewModel: RegisterViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(registerViewModel, navController)
        }
        composable(ROUTE_PASSWORD) {
            RecoveryPassword(registerViewModel, navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(registerViewModel, navController)
        }
    }
}