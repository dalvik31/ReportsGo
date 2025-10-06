package com.epacheco.reports.compose_reformat.ui.home


import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.DetailClientScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view.ClientsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances.FinancesScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail.OrdersScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product.NewProductScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales.SalesScreen
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeNavigationItem
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeRoutes
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.utils.extensions.serializableType
import com.google.android.material.tabs.TabItem
import kotlin.math.round
import kotlin.reflect.typeOf

@Composable
fun HomeScreen(onNavigateToRegister: () -> Unit) {

    /*val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()

    var curDestination by remember { mutableStateOf(0) }*/
    val items = BottomHomeNavigationItem().bottomNavigationItems()
    val bottomNavController = rememberNavController()
    val currentTopLevelDestination by bottomNavController.currentTabItemAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar() {
                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {

                    items.forEachIndexed { index, navigationItem ->
                        val isTabSelected = index == currentTopLevelDestination
                        NavigationBarItem(
                            selected = isTabSelected,
                            label = { Text(stringResource(navigationItem.label)) },
                            icon = {
                                Icon(
                                    ImageVector.vectorResource(navigationItem.icon),
                                    contentDescription = stringResource(navigationItem.label)
                                )
                            },
                            colors = NavigationBarItemColors(
                                selectedIconColor = RedDark,
                                unselectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedTextColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = RedDark,
                                selectedIndicatorColor = Color.Transparent,
                                disabledIconColor = MaterialTheme.colorScheme.onPrimary,
                                disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            onClick = {
                                bottomNavController.navigate(route = navigationItem.bottomHomeRoutes) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(bottomNavController.graph.findStartDestination().id) {
                                        saveState = !isTabSelected
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = !isTabSelected
                                }

                            }
                        )
                    }
                }
            }
            /*AnimatedNavigationBar(
                buttons = BottomHomeNavigationItem().bottomNavigationItems(),
                barColor = MaterialTheme.colorScheme.primaryContainer,
                circleColor = MaterialTheme.colorScheme.primaryContainer,
                selectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedColor = MaterialTheme.colorScheme.inversePrimary,
                bottomNavController
            )*/
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomHomeRoutes.MainOrdersBottomHomeRoute,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable<BottomHomeRoutes.MainOrdersBottomHomeRoute> {
                OrdersMainScreen { mainOrderId, orderSeason, orderNameMain ->
                    bottomNavController.navigate(
                        BottomHomeRoutes.DetailMainOrdersBottomHomeRoute(
                            mainOrderId,
                            orderSeason = orderSeason,
                            orderNameMain
                        )
                    )
                }
            }
            composable<BottomHomeRoutes.ClientBottomHomeRoute> {
                ClientsScreen() { idClient ->
                    bottomNavController.navigate(
                        BottomHomeRoutes.ClientDetailBottomHomeRoute(
                            idClient
                        )
                    )
                }
            }
            composable<BottomHomeRoutes.ClientDetailBottomHomeRoute> { backStackEntry ->
                val createDetailRout: BottomHomeRoutes.ClientDetailBottomHomeRoute =
                    backStackEntry.toRoute()
                DetailClientScreen(clientId = createDetailRout.idClient)
            }
            composable<BottomHomeRoutes.ProductBottomHomeRoute> {
                ProductsScreen(onNavigateToProductDetail = { productId ->
                    bottomNavController.navigate(
                        BottomHomeRoutes.CreateProductBottomHomeRoute(
                            productId = productId
                        )
                    )
                })
            }
            composable<BottomHomeRoutes.SaleBottomHomeRoute> {
                SalesScreen()
            }
            composable<BottomHomeRoutes.ProfileBottomHomeRoute> {
                ProfileScreen(
                    onNavigateToLogin = {
                        onNavigateToRegister.invoke()
                    }, onNavigateToFinances = {
                        goProfileToRoute(bottomNavController, BottomHomeRoutes.SaleBottomHomeRoute)
                    }, onNavigateToOrders = {
                        bottomNavController.navigate(
                            route =
                                BottomHomeRoutes.MainOrdersBottomHomeRoute,
                        ) {
                            popUpTo(BottomHomeRoutes.MainOrdersBottomHomeRoute) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToClients = {
                        goProfileToRoute(
                            bottomNavController,
                            BottomHomeRoutes.ClientBottomHomeRoute
                        )
                    },
                    onNavigateToProducts = {
                        goProfileToRoute(
                            bottomNavController,
                            BottomHomeRoutes.ProductBottomHomeRoute
                        )
                    })
            }
            composable<BottomHomeRoutes.DetailMainOrdersBottomHomeRoute> { backStackEntry ->
                val orderMainRoute: BottomHomeRoutes.DetailMainOrdersBottomHomeRoute =
                    backStackEntry.toRoute()
                OrdersScreen(
                    mainOrderId = orderMainRoute.idOrderMain,
                    orderSeason = orderMainRoute.orderSeason,
                    nameOrderMain = orderMainRoute.nameOrderMain,

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


            composable<BottomHomeRoutes.CreateProductBottomHomeRoute> { backStackEntry ->
                val createProductRoute: BottomHomeRoutes.CreateProductBottomHomeRoute =
                    backStackEntry.toRoute()
                NewProductScreen(productToEdit = createProductRoute.productId, onBackPressed = {
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


private fun goProfileToRoute(
    bottomNavController: NavHostController,
    bottomHomeRoutes: BottomHomeRoutes
) {
    bottomNavController.navigate(
        route =
            bottomHomeRoutes,
    ) {
        popUpTo(BottomHomeRoutes.ProfileBottomHomeRoute) {
            inclusive = true
        }
    }
}

@Composable
private fun NavController.currentTabItemAsState(): State<Int> {
    val selectedItem = remember { mutableIntStateOf(0) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == BottomHomeRoutes.MainOrdersBottomHomeRoute.javaClass.canonicalName } -> {
                    selectedItem.intValue = 0
                }
                destination.hierarchy.any { it.route == BottomHomeRoutes.ClientBottomHomeRoute.javaClass.canonicalName } -> {
                    selectedItem.intValue = 1
                }

                destination.hierarchy.any { it.route == BottomHomeRoutes.ProductBottomHomeRoute.javaClass.canonicalName } -> {
                    selectedItem.intValue = 2
                }

                destination.hierarchy.any { it.route == BottomHomeRoutes.SaleBottomHomeRoute.javaClass.canonicalName } -> {
                    selectedItem.intValue = 3
                }

                destination.hierarchy.any { it.route == BottomHomeRoutes.ProfileBottomHomeRoute.javaClass.canonicalName } -> {
                    selectedItem.intValue = 4
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen({})
}