package com.epacheco.reports.compose_reformat.ui.navigation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.account.AccountScreen
import com.epacheco.reports.compose_reformat.ui.home.HomeScreen
import com.epacheco.reports.compose_reformat.ui.recovery_password.PasswordScreen
import com.epacheco.reports.compose_reformat.ui.splash.SplashScreen
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun ReportsNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    ctx: Activity
) {
    ReportsGoTheme {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = NavHostScreens.SplashRoute
        ) {

            composable<NavHostScreens.SplashRoute> {

                    SplashScreen(onNavigateToHome = {

                            navController.navigate(NavHostScreens.HomeRoute) {
                                popUpTo(NavHostScreens.SplashRoute) { inclusive = true }
                            }


                    }, onNavigateToLogin = {
                        navController.navigate(NavHostScreens.RegisterRoute) {
                            popUpTo(NavHostScreens.SplashRoute) { inclusive = true }
                        }
                    })


            }
            composable<NavHostScreens.RegisterRoute> {
                AccountScreen(onNavigateToHome = {
                    navController.navigate(NavHostScreens.HomeRoute) {
                        popUpTo(NavHostScreens.RegisterRoute) { inclusive = true }
                    }
                }, onNavigateToPassword = {
                    navController.navigate(NavHostScreens.PasswordRoute)
                })
            }
            composable<NavHostScreens.PasswordRoute> {
                PasswordScreen(onBackPressed = {
                    navController.navigateUp()
                })
            }
            composable<NavHostScreens.HomeRoute> {
                HomeScreen(ctx = ctx,onNavigateToRegister = {
                    navController.navigate(NavHostScreens.RegisterRoute) {
                        popUpTo(NavHostScreens.HomeRoute) { inclusive = true }
                    }
                })
            }
        }

    }


}