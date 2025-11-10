package com.epacheco.reports.compose_reformat.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.ClientsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_detail.ClientDetailScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info.ClientInfoScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_orders.ClientOrderScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.create_client.CreateClientScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances.FinancesScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances.finances_date.FinancesDateScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail.OrdersScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product.NewProductScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales.SalesScreen
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeNavigationItem
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeRoutes
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeRoutes.MainOrdersBottomHomeRoute
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.utils.extensions.fromPath

@Composable
fun HomeScreen(onNavigateToRegister: () -> Unit) {
    val navController = rememberNavController()
    val items = BottomHomeNavigationItem().bottomNavigationItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute?.fromPath() in listOf(
        MainOrdersBottomHomeRoute.javaClass.canonicalName.substringBefore(".Companion"),
        BottomHomeRoutes.ClientBottomHomeRoute.javaClass.canonicalName.substringBefore(".Companion"),
        BottomHomeRoutes.ProductBottomHomeRoute.javaClass.canonicalName.substringBefore(".Companion"),
        BottomHomeRoutes.SaleBottomHomeRoute.javaClass.canonicalName.substringBefore(".Companion"),

        )



    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }), // Slide in from bottom
                exit = slideOutVertically(targetOffsetY = { it }) // Slide out to bottom
            ) {
                NavigationBar {

                    items.forEach { navigationItem ->

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    ImageVector.vectorResource(navigationItem.icon),
                                    contentDescription = stringResource(navigationItem.label)
                                )
                            },
                            label = { Text(stringResource(navigationItem.label)) },
                            selected = currentRoute?.fromPath() == navigationItem.route.fromPath(),
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
                                navController.navigate(navigationItem.bottomHomeRoutes) {
                                    popUpTo(navController.graph.startDestinationId) {
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


        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = MainOrdersBottomHomeRoute(),
            modifier = Modifier.padding(innerPadding)
        ) {

            composable<MainOrdersBottomHomeRoute> { backStackEntry ->
                val clientIdFromClientDetail =
                    backStackEntry.toRoute<MainOrdersBottomHomeRoute>().idClient
                OrdersMainScreen(
                    onNavigateToProfile = {
                        navController.navigate(
                            BottomHomeRoutes.ProfileBottomHomeRoute
                        )
                    },
                    onNavigateToElementsMain = { mainOrderId, orderSeason, orderNameMain ->
                        navController.navigate(
                            BottomHomeRoutes.DetailMainOrdersBottomHomeRoute(
                                mainOrderId,
                                orderSeason = orderSeason,
                                orderNameMain
                            )
                        )
                    }, clientId = clientIdFromClientDetail
                )
            }
            composable<BottomHomeRoutes.ClientBottomHomeRoute> { backStackEntry ->
                val clientIsSelect: BottomHomeRoutes.ClientBottomHomeRoute =
                    backStackEntry.toRoute()
                ClientsScreen(
                    onNavigateToProfile = {
                        navController.navigate(
                            BottomHomeRoutes.ProfileBottomHomeRoute
                        )
                    },
                    onNavigateToClientDetail = { idClient ->
                        navController.navigate(
                            BottomHomeRoutes.ClientDetailBottomHomeRoute(
                                idClient
                            )
                        )

                    },
                    onNavigateToCreateClient = { idClient ->
                        navController.navigate(
                            BottomHomeRoutes.ClientCreateNewBottomHomeRoute(
                                idClient
                            )
                        )
                    },
                    isSelectableClient = clientIsSelect.isSelectableClient,
                    onClientSelected = { clientName ->
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "resultStatus",
                            clientName
                        )
                        navController.navigateUp()
                    })
            }
            composable<BottomHomeRoutes.ClientDetailBottomHomeRoute> { backStackEntry ->
                val createDetailRout: BottomHomeRoutes.ClientDetailBottomHomeRoute =
                    backStackEntry.toRoute()
                ClientDetailScreen(
                    clientId = createDetailRout.idClient,
                    onBackPressed = {
                        navController.navigateUp()
                    },
                    openClientTransaction = {
                        navController.navigate(
                            BottomHomeRoutes.ClientDetailInformation(
                                it
                            )
                        )
                    },
                    openClientSale = { clientId ->
                        navController.navigate(
                            route = BottomHomeRoutes.SaleBottomHomeRoute(clientId),
                        )
                    },
                    openClientOrder = { clientId ->
                        navController.navigate(
                            BottomHomeRoutes.ClientOrdersBottomHomeRoute(idClient = clientId)
                        )
                    },
                )
            }
            composable<BottomHomeRoutes.ClientCreateNewBottomHomeRoute> { backStackEntry ->
                val clientId: BottomHomeRoutes.ClientCreateNewBottomHomeRoute =
                    backStackEntry.toRoute()
                CreateClientScreen(clientId = clientId.idClient, onBackPressed = {
                    navController.navigateUp()
                })

            }
            composable<BottomHomeRoutes.ProductBottomHomeRoute> { backStackEntry ->
                val productIsSelect: BottomHomeRoutes.ProductBottomHomeRoute =
                    backStackEntry.toRoute()
                ProductsScreen(onNavigateToProductDetail = { productId ->
                    navController.navigate(
                        BottomHomeRoutes.CreateProductBottomHomeRoute(
                            productId = productId
                        )
                    )
                }, onNavigateToProfile = {
                    navController.navigate(
                        BottomHomeRoutes.ProfileBottomHomeRoute
                    )
                }, isSelectableProduct = productIsSelect.isSelectableProduct, onProductSelected = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "productResult",
                        it
                    )
                    navController.navigateUp()
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
                    navController.navigate(
                        BottomHomeRoutes.FinancesBottomHomeRoute
                    )
                }, onNavigateToProfile = {
                    navController.navigate(
                        BottomHomeRoutes.ProfileBottomHomeRoute
                    )
                }, onNavigateToSelectClient = {
                    navController.navigate(
                        BottomHomeRoutes.ClientBottomHomeRoute(
                            isSelectableClient = true
                        )
                    ) {
                        popUpTo(BottomHomeRoutes.SaleBottomHomeRoute()) {
                            inclusive = false
                        }
                    }


                }, onNavigateToSelectProduct = {
                    navController.navigate(
                        BottomHomeRoutes.ProductBottomHomeRoute(
                            isSelectableProduct = true
                        )
                    ) {
                        popUpTo(BottomHomeRoutes.SaleBottomHomeRoute()) {
                            inclusive = false
                        }
                    }
                }, clientIdSelected = clientId, productIdSelected = onProductResult.value)

            }

            composable<BottomHomeRoutes.FinancesBottomHomeRoute> { backStackEntry ->

                val finalDate =
                    backStackEntry.savedStateHandle.getLiveData<String>("finalDate")
                        .observeAsState("")

                val initialDate =
                    backStackEntry.savedStateHandle.getLiveData<String>("initialDate")
                        .observeAsState("")
                FinancesScreen(
                    initialDate = initialDate.value,
                    finalDate = finalDate.value,
                    onBackPressed = {
                        navController.navigateUp()
                    },
                    onSelectDateScreen = {
                        navController.navigate(
                            BottomHomeRoutes.FinancesDateBottomHomeRoute
                        ) {
                            popUpTo(BottomHomeRoutes.FinancesBottomHomeRoute) {
                                inclusive = false
                            }
                        }
                    })
            }
            composable<BottomHomeRoutes.FinancesDateBottomHomeRoute> { backStackEntry ->
                FinancesDateScreen(onDateSelected = { initialDate, finalDate ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "initialDate",
                        initialDate
                    )


                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "finalDate",
                        finalDate
                    )
                    navController.navigateUp()
                }
                )
            }

            composable<BottomHomeRoutes.CreateOrderBottomHomeRoute> { backStackEntry ->
                val orderMainRoute: BottomHomeRoutes.CreateOrderBottomHomeRoute =
                    backStackEntry.toRoute()

                var clientId: String? = null
                val clientIdFromOrder =
                    backStackEntry.savedStateHandle.getLiveData<String>("resultStatus")
                        .observeAsState("")

                if (orderMainRoute.clientId != null) {
                    clientId = orderMainRoute.clientId
                } else if (clientIdFromOrder.value != null) {
                    clientId = clientIdFromOrder.value
                }
                backStackEntry.savedStateHandle.remove<String>("resultStatus")
                backStackEntry.savedStateHandle.remove<String>("productResult")
                NewOrderScreen(
                    orderSeason = orderMainRoute.orderSeason,
                    mainOrderId = orderMainRoute.idOrderMain,
                    orderToEdit = orderMainRoute.orderToEdit,
                    onNavigateToSelectClient = {
                        navController.navigate(
                            BottomHomeRoutes.ClientBottomHomeRoute(
                                isSelectableClient = true
                            )
                        ) {
                            popUpTo(
                                BottomHomeRoutes.CreateOrderBottomHomeRoute(
                                    null,
                                    null,
                                    null
                                )
                            ) {
                                inclusive = false
                            }
                        }
                    },
                    clientIdSelected = clientId,
                    onBackPressed = {
                        navController.navigateUp()
                    })
            }
            composable<BottomHomeRoutes.ProfileBottomHomeRoute> {
                ProfileScreen(onNavigateToLogin = { onNavigateToRegister.invoke() })
            }
            composable<BottomHomeRoutes.ClientDetailInformation> { backStackEntry ->
                val orderMainRoute: BottomHomeRoutes.ClientDetailInformation =
                    backStackEntry.toRoute()
                ClientInfoScreen(clientId = orderMainRoute.idClient, onBackPressed = {
                    navController.navigateUp()
                })
            }

            composable<BottomHomeRoutes.ClientOrdersBottomHomeRoute> { backStackEntry ->
                val clientId: BottomHomeRoutes.ClientOrdersBottomHomeRoute =
                    backStackEntry.toRoute()
                ClientOrderScreen(clientId = clientId.idClient, onBackPressed = {
                    navController.navigateUp()
                }, onOrderMainSelected = { mainOrderId, orderSeason, orderNameMain, openOrder ->
                    navController.navigate(
                        BottomHomeRoutes.CreateOrderBottomHomeRoute(
                            null,
                            mainOrderId,
                            orderSeason,
                            clientId = openOrder
                        )
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
                        navController.navigate(
                            BottomHomeRoutes.CreateOrderBottomHomeRoute(
                                null,
                                mainOrderId,
                                orderSeason,
                                clientId = orderMainRoute.clientId
                            )
                        )
                    },
                    onNavigateToEditOrder = { orderMainId, orderId ->
                        navController.navigate(
                            BottomHomeRoutes.CreateOrderBottomHomeRoute(
                                orderId,
                                orderMainId,
                                null,
                            )
                        )
                    },
                    onBackPressed = {
                        navController.navigateUp()
                    }, clientId = orderMainRoute.clientId
                )
            }


            composable<BottomHomeRoutes.CreateProductBottomHomeRoute> { backStackEntry ->
                val createProductRoute: BottomHomeRoutes.CreateProductBottomHomeRoute =
                    backStackEntry.toRoute()
                NewProductScreen(productToEdit = createProductRoute.productId, onBackPressed = {
                    navController.navigateUp()
                })
            }


        }
    }
}