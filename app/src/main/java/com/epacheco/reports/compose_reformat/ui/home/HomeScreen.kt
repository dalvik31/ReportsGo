package com.epacheco.reports.compose_reformat.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.ClientsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.OrdersScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.ProductsScreen
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomNavHostScreens
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomNavigationItem
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel

@Composable
fun HomeScreen(registerViewModel: RegisterViewModel?, navController: NavHostController) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                            Text(stringResource(navigationItem.label))
                        },
                        icon = {
                            Icon(
                                ImageVector.vectorResource(navigationItem.icon),
                                contentDescription = stringResource(navigationItem.label)
                            )
                        },
                        onClick = {
                            bottomNavController.navigate(navigationItem.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavHostScreens.ORDERS.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(BottomNavHostScreens.ORDERS.route) {
                OrdersScreen(
                    registerViewModel,
                    navController
                )
            }
            composable(BottomNavHostScreens.CLIENTS.route) {
                ClientsScreen(
                    registerViewModel,
                    navController
                )
            }
            composable(BottomNavHostScreens.PRODUCTS.route) {
                ProductsScreen(
                    registerViewModel,
                    navController
                )
            }
        }
    }
}