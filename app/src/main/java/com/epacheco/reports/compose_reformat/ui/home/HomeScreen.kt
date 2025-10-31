package com.epacheco.reports.compose_reformat.ui.home


import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info.ClientInfoScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.ClientDetailScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.NewClientScreen
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
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {
            composable<BottomHomeRoutes.MainOrdersBottomHomeRoute> {
                OrdersMainScreen(
                    onNavigateToProfile = {
                        bottomNavController.navigate(
                            BottomHomeRoutes.ProfileBottomHomeRoute
                        )
                    },
                    onNavigateToElementsMain = { mainOrderId, orderSeason, orderNameMain ->
                        bottomNavController.navigate(
                            BottomHomeRoutes.DetailMainOrdersBottomHomeRoute(
                                mainOrderId,
                                orderSeason = orderSeason,
                                orderNameMain
                            )
                        )
                    })
            }
            composable<BottomHomeRoutes.ClientBottomHomeRoute> { backStackEntry ->
                val clientIsSelect: BottomHomeRoutes.ClientBottomHomeRoute =
                    backStackEntry.toRoute()
                ClientsScreen(
                    onNavigateToProfile = {
                        bottomNavController.navigate(
                            BottomHomeRoutes.ProfileBottomHomeRoute
                        )
                    },
                    onNavigateToClientDetail = { idClient ->
                        bottomNavController.navigate(
                            BottomHomeRoutes.ClientDetailBottomHomeRoute(
                                idClient
                            )
                        )

                    },
                    onNavigateToCreateClient = { idClient ->
                        bottomNavController.navigate(
                            BottomHomeRoutes.ClientCreateNewBottomHomeRoute(
                                idClient
                            )
                        )
                    },
                    isSelectableClient = clientIsSelect.isSelectableClient,
                    onClientSelected = { clientName ->
                        bottomNavController.previousBackStackEntry?.savedStateHandle?.set(
                            "resultStatus",
                            clientName
                        )
                        bottomNavController.navigateUp()
                    })
            }
            composable<BottomHomeRoutes.ClientDetailBottomHomeRoute> { backStackEntry ->
                val createDetailRout: BottomHomeRoutes.ClientDetailBottomHomeRoute =
                    backStackEntry.toRoute()
                ClientDetailScreen(clientId = createDetailRout.idClient, onBackPressed = {
                    bottomNavController.navigateUp()
                }, openClientTransaction = {
                    bottomNavController.navigate(
                        BottomHomeRoutes.ClientDetailInformation(
                            it
                        )
                    )
                }, openClientSale = { clientId ->

                    //bottomNavController.previousBackStackEntry?.savedStateHandle?.set("resultStatus", clientId)

                    bottomNavController.navigate(
                        route = BottomHomeRoutes.SaleBottomHomeRoute(clientId),
                    )
                })
            }
            composable<BottomHomeRoutes.ClientCreateNewBottomHomeRoute> { backStackEntry ->
                val clientId: BottomHomeRoutes.ClientCreateNewBottomHomeRoute =
                    backStackEntry.toRoute()
                NewClientScreen(clientId = clientId.idClient, onBackPressed = {
                    bottomNavController.navigateUp()
                })

            }
            composable<BottomHomeRoutes.ProductBottomHomeRoute> { backStackEntry ->
                val productIsSelect: BottomHomeRoutes.ProductBottomHomeRoute =
                    backStackEntry.toRoute()
                ProductsScreen(onNavigateToProductDetail = { productId ->
                    bottomNavController.navigate(
                        BottomHomeRoutes.CreateProductBottomHomeRoute(
                            productId = productId
                        )
                    )
                }, onNavigateToProfile = {
                    bottomNavController.navigate(
                        BottomHomeRoutes.ProfileBottomHomeRoute
                    )
                }, isSelectableProduct = productIsSelect.isSelectableProduct, onProductSelected = {
                    bottomNavController.previousBackStackEntry?.savedStateHandle?.set(
                        "productResult",
                        it
                    )
                    bottomNavController.navigateUp()
                })
            }
            composable<BottomHomeRoutes.SaleBottomHomeRoute> { backStackEntry ->
                var clientId: String? = null
                val clientIdFromClientDetail =
                    backStackEntry.toRoute<BottomHomeRoutes.SaleBottomHomeRoute>().idClient
                val clientIdFromSale =
                    backStackEntry.savedStateHandle.getLiveData<String>("resultStatus")
                        .observeAsState("")
                if (clientIdFromClientDetail != null)
                    clientId = clientIdFromClientDetail
                else if (clientIdFromSale.value != null) {
                    clientId = clientIdFromSale.value
                }

                val onProductResult =
                    backStackEntry.savedStateHandle.getLiveData<String>("productResult")
                        .observeAsState("")
                backStackEntry.savedStateHandle.remove<String>("resultStatus")
                backStackEntry.savedStateHandle.remove<String>("productResult")
                SalesScreen(onNavigateToFinances = {
                    bottomNavController.navigate(
                        BottomHomeRoutes.FinancesBottomHomeRoute
                    )
                }, onNavigateToProfile = {
                    bottomNavController.navigate(
                        BottomHomeRoutes.ProfileBottomHomeRoute
                    )
                }, onNavigateToSelectClient = {
                    bottomNavController.navigate(
                        BottomHomeRoutes.ClientBottomHomeRoute(
                            isSelectableClient = true
                        )
                    ) {
                        popUpTo(BottomHomeRoutes.SaleBottomHomeRoute()) {
                            inclusive = false
                        }
                    }


                }, onNavigateToSelectProduct = {
                    bottomNavController.navigate(
                        BottomHomeRoutes.ProductBottomHomeRoute(
                            isSelectableProduct = true
                        )
                    ) {
                        popUpTo(BottomHomeRoutes.SaleBottomHomeRoute) {
                            inclusive = false
                        }
                    }
                }, clientIdSelected = clientId, productIdSelected = onProductResult.value)

            }

            composable<BottomHomeRoutes.FinancesBottomHomeRoute> {
                FinancesScreen(onBackPressed = {
                    bottomNavController.navigateUp()
                })
            }
            composable<BottomHomeRoutes.ProfileBottomHomeRoute>(


            ) {
                ProfileScreen(
                    onNavigateToLogin = {
                        onNavigateToRegister.invoke()
                    }, onNavigateToFinances = {
                        goProfileToRoute(
                            bottomNavController,
                            BottomHomeRoutes.SaleBottomHomeRoute(null)
                        )
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
                            BottomHomeRoutes.ClientBottomHomeRoute()
                        )
                    },
                    onNavigateToProducts = {
                        goProfileToRoute(
                            bottomNavController,
                            BottomHomeRoutes.ProductBottomHomeRoute()
                        )
                    })
            }
            composable<BottomHomeRoutes.ClientDetailInformation> { backStackEntry ->
                val orderMainRoute: BottomHomeRoutes.ClientDetailInformation =
                    backStackEntry.toRoute()
                ClientInfoScreen(clientId = orderMainRoute.idClient, onBackPressed = {
                    bottomNavController.navigateUp()
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

            destination.hierarchy.forEachIndexed { index, navigationItem ->
                if (navigationItem.route == BottomHomeRoutes.MainOrdersBottomHomeRoute.javaClass.canonicalName
                ) {
                    selectedItem.intValue = 0
                    return@forEachIndexed
                }
                if (navigationItem.route == BottomHomeRoutes.ClientBottomHomeRoute.javaClass.canonicalName
                ) {
                    selectedItem.intValue = 1
                    return@forEachIndexed
                }
                if (navigationItem.route == BottomHomeRoutes.ProductBottomHomeRoute.javaClass.canonicalName
                ) {
                    selectedItem.intValue = 2
                    return@forEachIndexed
                }
                if (navigationItem.route == BottomHomeRoutes.SaleBottomHomeRoute.javaClass.canonicalName
                ) {
                    selectedItem.intValue = 3
                    return@forEachIndexed
                }


            }
            /*destination.hierarchy.any {
                Log.e("aqui","MainOrdersBottomHomeRoute: vanoooos1: ${  it.route}")
                Log.e("aqui","MainOrdersBottomHomeRoute: vanoooo2: ${   BottomHomeRoutes.MainOrdersBottomHomeRoute.javaClass.canonicalName}")




                it.route == BottomHomeRoutes.MainOrdersBottomHomeRoute.javaClass.canonicalName


            }*/
            /*when {
                destination.hierarchy.any {
                    Log.e("aqui","MainOrdersBottomHomeRoute: vanoooos1: ${  it.route}")
                    Log.e("aqui","MainOrdersBottomHomeRoute: vanoooo2: ${   BottomHomeRoutes.MainOrdersBottomHomeRoute.javaClass.canonicalName}")
                    it.route == BottomHomeRoutes.MainOrdersBottomHomeRoute.javaClass.canonicalName


                } -> {
                    selectedItem.intValue = 0
                }


                destination.hierarchy.any {
                    Log.e("aqui","ClientBottomHomeRoute: vanoooos1: ${  it.route}")
                    Log.e("aqui","ClientBottomHomeRoute: vanoooos1: ${  it.route}")
                    Log.e("aqui","ClientBottomHomeRoute: vanoooos2: ${   BottomHomeRoutes.ClientBottomHomeRoute.javaClass.simpleName}")

                    it.label == BottomHomeRoutes.ClientBottomHomeRoute.javaClass.canonicalName
                } -> {
                    selectedItem.intValue = 1
                }

                destination.hierarchy.any { it.route == BottomHomeRoutes.ProductBottomHomeRoute.javaClass.canonicalName } -> {
                    selectedItem.intValue = 2
                }

                destination.hierarchy.any { it.route == BottomHomeRoutes.SaleBottomHomeRoute.javaClass.canonicalName } -> {
                    selectedItem.intValue = 3
                }
            }*/
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