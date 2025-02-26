package com.epacheco.reports.compose_reformat.ui.home


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.general_components.navbar.AnimatedNavigationBar
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.ClientsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances.FinancesScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.OrdersScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileScreen
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomNavHostScreens
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomNavigationItem
import com.epacheco.reports.compose_reformat.ui.theme.White

@Composable
fun HomeScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedNavigationBar(
                buttons = BottomNavigationItem().bottomNavigationItems(),
                barColor = MaterialTheme.colorScheme.primary,
                circleColor = MaterialTheme.colorScheme.primary,
                selectedColor = White,
                unselectedColor = Color.Gray,
                bottomNavController
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavHostScreens.ORDERS.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(BottomNavHostScreens.ORDERS.route) {
                OrdersScreen(navHostController = navController)
            }
            composable(BottomNavHostScreens.CLIENTS.route) {
                ClientsScreen()
            }
            composable(BottomNavHostScreens.PRODUCTS.route) {
                ProductsScreen()
            }
            composable(BottomNavHostScreens.FINANCES.route) {
                FinancesScreen()
            }
            composable(BottomNavHostScreens.PROFILE.route) {
                ProfileScreen(
                    navController = navController
                )
            }

        }
    }
}
