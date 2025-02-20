package com.epacheco.reports.compose_reformat.ui.home.navigation

sealed class BottomNavHostScreens(val route: String) {
    data object ORDERS : BottomNavHostScreens("orders_screen")
    data object CLIENTS : BottomNavHostScreens("clients_screen")
    data object PRODUCTS : BottomNavHostScreens("products_screen")
}
