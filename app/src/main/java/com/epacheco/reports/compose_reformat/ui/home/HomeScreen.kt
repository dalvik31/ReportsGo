package com.epacheco.reports.compose_reformat.ui.home


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.epacheco.reports.compose_reformat.general_components.navbar.AnimatedNavigationBar
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.ClientsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances.FinancesScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail.OrdersScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsScreen
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileScreen
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeNavigationItem
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeRoutes
import com.epacheco.reports.compose_reformat.ui.home.navigation.parcelableType
import com.epacheco.reports.compose_reformat.ui.theme.White
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val bottomNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedNavigationBar(
                buttons = BottomHomeNavigationItem().bottomNavigationItems(),
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
                ProfileScreen(onLogout = {
                    onLogout.invoke()
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
                        Log.e("auqi","estamoosc ${it.orderSizeNumeric}}")
                        bottomNavController.navigate(
                            BottomHomeRoutes.CreateOrderBottomHomeRoute(
                                it,
                                "",
                                null
                            )
                        )
                    },
                    onBackPressed = {
                        bottomNavController.popBackStack()
                    })
            }


            composable<BottomHomeRoutes.CreateOrderBottomHomeRoute>(    typeMap = mapOf(typeOf<Order?>() to serializableType<Order?>())) { backStackEntry ->
                val orderMainRoute: BottomHomeRoutes.CreateOrderBottomHomeRoute =
                    backStackEntry.toRoute()

                NewOrderScreen(
                    orderSeason = orderMainRoute.orderSeason,
                    mainOrderId = orderMainRoute.idOrderMain,
                    orderToEdit =orderMainRoute.orderId,
                    onBackPressed = {
                        bottomNavController.popBackStack()
                    })
            }
        }
    }
}

val BookType = object : NavType<Order?>(
    isNullableAllowed = true
) {
    override fun get(bundle: Bundle, key: String): Order? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Order::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Order {
        return Json.decodeFromString<Order>(value)
    }

    override fun serializeAsValue(value: Order?): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Order?) {
        bundle.putParcelable(key, value)
    }
}


inline fun <reified T : Any?> serializableType(
    isNullableAllowed: Boolean = true,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}