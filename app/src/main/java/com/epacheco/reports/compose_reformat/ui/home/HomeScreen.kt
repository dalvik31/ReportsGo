package com.epacheco.reports.compose_reformat.ui.home


import android.app.Activity
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.navbar.AnimatedNavigationBar
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
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeNavigationItem
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeRoutes
import com.epacheco.reports.compose_reformat.ui.theme.GreenColor
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.White
import com.epacheco.reports.compose_reformat.ui.theme.White40
import com.epacheco.reports.compose_reformat.ui.theme.YellowColor
import com.epacheco.reports.compose_reformat.utils.extensions.serializableType
import com.epacheco.reports.tools.ScreenManager
import kotlin.reflect.typeOf

@Composable
fun HomeScreen(onNavigateToRegister: () -> Unit) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()

    val items = BottomHomeNavigationItem().bottomNavigationItems()
    var curDestination by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar() {
                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                    items.forEachIndexed { index, navigationItem ->

                        NavigationBarItem(
                            selected = curDestination == index,
                            label = { Text(stringResource(navigationItem.label)) },
                            icon = {
                                Icon(
                                    ImageVector.vectorResource(navigationItem.icon),
                                    contentDescription = stringResource(navigationItem.label)
                                )
                            },
                            colors = NavigationBarItemColors(
                                selectedIconColor = RedDark,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                                selectedTextColor = RedDark,
                                selectedIndicatorColor = Color.Transparent,
                                disabledIconColor = MaterialTheme.colorScheme.onPrimary,
                                disabledTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            onClick = {
                                curDestination = index
                                bottomNavController.navigate(navigationItem.bottomHomeRoutes)
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
            composable<BottomHomeRoutes.FinanceBottomHomeRoute> {
                LaunchedEffect(Unit) {

                }
                //FinancesScreen()
            }
            composable<BottomHomeRoutes.ProfileBottomHomeRoute> {
                ProfileScreen(onNavigateToLogin = {
                    onNavigateToRegister.invoke()
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


@Preview
@Composable
fun HomePreview() {
    HomeScreen({})
}