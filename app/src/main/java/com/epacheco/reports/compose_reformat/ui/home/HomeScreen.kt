package com.epacheco.reports.compose_reformat.ui.home


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.epacheco.reports.compose_reformat.general_components.navbar.AnimatedNavigationBar
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.ClientsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances.FinancesScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail.OrdersScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileScreen
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeNavigationItem
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeRoutes
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.ui.theme.White40
import com.epacheco.reports.compose_reformat.utils.extensions.serializableType
import kotlin.reflect.typeOf

@Composable
fun HomeScreen(onNavigateToRegister: () -> Unit) {
    val bottomNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedNavigationBar(
                buttons = BottomHomeNavigationItem().bottomNavigationItems(),
                barColor = MaterialTheme.colorScheme.primary,
                circleColor = MaterialTheme.colorScheme.primary,
                selectedColor = White,
                unselectedColor = White40,
                bottomNavController
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomHomeRoutes.MainOrdersBottomHomeRoute,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable<BottomHomeRoutes.MainOrdersBottomHomeRoute> {
                OrdersMainScreen { mainOrderId, orderSeason ->
                    bottomNavController.navigate(
                        BottomHomeRoutes.DetailMainOrdersBottomHomeRoute(
                            mainOrderId,
                            orderSeason
                        )
                    )
                }
            }
            composable<BottomHomeRoutes.ClientBottomHomeRoute> {
                ClientsScreen()
            }
            composable<BottomHomeRoutes.ProductBottomHomeRoute> {
                ProductsScreen()
            }
            composable<BottomHomeRoutes.FinanceBottomHomeRoute> {
                FinancesScreen()
            }
            composable<BottomHomeRoutes.ProfileBottomHomeRoute> {
                ProfileScreen(onNavigateToRegister = {
                    onNavigateToRegister.invoke()
                })
            }
            composable<BottomHomeRoutes.DetailMainOrdersBottomHomeRoute> { backStackEntry ->
                val orderMainRoute: BottomHomeRoutes.DetailMainOrdersBottomHomeRoute =
                    backStackEntry.toRoute()
                OrdersScreen(
                    mainOrderId = orderMainRoute.idOrderMain,
                    orderSeason = orderMainRoute.orderSeason,

                    onNavigateToCreateOrder = { mainOrderId, orderSeason ->
                        bottomNavController.navigate(
                            BottomHomeRoutes.CreateOrderBottomHomeRoute(
                                null,
                                mainOrderId,
                                orderSeason
                            )
                        )
                    },
                    onNavigateToEditOrder = {
                        bottomNavController.navigate(
                            BottomHomeRoutes.CreateOrderBottomHomeRoute(
                                it,
                                "",
                                null
                            )
                        )
                    },
                    onBackPressed = {
                        bottomNavController.navigateUp()
                    })
            }


            composable<BottomHomeRoutes.CreateOrderBottomHomeRoute>(typeMap = mapOf(typeOf<Order?>() to serializableType<Order?>())) { backStackEntry ->
                val orderMainRoute: BottomHomeRoutes.CreateOrderBottomHomeRoute =
                    backStackEntry.toRoute()

                NewOrderScreen(
                    orderSeason = orderMainRoute.orderSeason,
                    mainOrderId = orderMainRoute.idOrderMain,
                    orderToEdit = orderMainRoute.orderToEdit,
                    onBackPressed = {
                        bottomNavController.navigateUp()
                    })
            }
        }
    }
}
