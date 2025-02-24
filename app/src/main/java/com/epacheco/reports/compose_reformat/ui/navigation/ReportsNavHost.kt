package com.epacheco.reports.compose_reformat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.home.HomeScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.OrdersViewModel
import com.epacheco.reports.compose_reformat.ui.login.RegisterScreen
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel
import com.epacheco.reports.compose_reformat.ui.recovery_password.PasswordScreen
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun ReportsNavHost(
    registerViewModel: RegisterViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    ReportsGoTheme {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = if (registerViewModel.isUserLogin()) NavHostScreens.HOME.route else NavHostScreens.REGISTER.route
        ) {
            composable(NavHostScreens.REGISTER.route) {
                RegisterScreen(modifier = modifier, registerViewModel, navController)
            }
            composable(NavHostScreens.PASSWORD.route) {
                PasswordScreen(registerViewModel, navController)
            }
            composable(NavHostScreens.HOME.route) {
                HomeScreen(registerViewModel, navController)
            }
        }

    }


}