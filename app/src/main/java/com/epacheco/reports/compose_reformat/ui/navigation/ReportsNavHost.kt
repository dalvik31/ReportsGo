package com.epacheco.reports.compose_reformat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.home.HomeScreen
import com.epacheco.reports.compose_reformat.ui.recovery_password.PasswordScreen
import com.epacheco.reports.compose_reformat.ui.account.AccountScreen
import com.epacheco.reports.compose_reformat.ui.splash.SplashScreen
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
            startDestination = NavHostScreens.SPLASH.route
        ) {

            composable(NavHostScreens.SPLASH.route) {
                SplashScreen(onNavigateToHome = {
                    navController.navigate(NavHostScreens.HOME.route) {
                        popUpTo(NavHostScreens.SPLASH.route) { inclusive = true }
                    }
                }, onNavigateToLogin = {
                    navController.navigate(NavHostScreens.REGISTER.route) {
                        popUpTo(NavHostScreens.SPLASH.route) { inclusive = true }
                    }
                })
            }
            composable(NavHostScreens.REGISTER.route) {
                AccountScreen(onNavigateToHome = {
                    navController.navigate(NavHostScreens.HOME.route) {
                        popUpTo(NavHostScreens.REGISTER.route) { inclusive = true }
                    }
                }, onNavigateToPassword = {
                    navController.navigate(NavHostScreens.PASSWORD.route)
                })
            }
            composable(NavHostScreens.PASSWORD.route) {
                PasswordScreen(navController)
            }
            composable(NavHostScreens.HOME.route) {
                HomeScreen(navController = navController, onLogout = {
                    navController.navigate(NavHostScreens.REGISTER.route) {
                        popUpTo(NavHostScreens.SPLASH.route) { inclusive = true }
                    }
                })
            }
        }

    }


}