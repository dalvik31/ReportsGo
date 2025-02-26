package com.epacheco.reports.compose_reformat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.home.HomeScreen
import com.epacheco.reports.compose_reformat.ui.login.RegisterScreen
import com.epacheco.reports.compose_reformat.ui.recovery_password.PasswordScreen
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun ReportsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    ReportsGoTheme {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = NavHostScreens.REGISTER.route
        ) {
            composable(NavHostScreens.REGISTER.route) {
                RegisterScreen(navController = navController)
            }
            composable(NavHostScreens.PASSWORD.route) {
                PasswordScreen(navController)
            }
            composable(NavHostScreens.HOME.route) {
                HomeScreen(navController = navController)
            }
        }

    }


}