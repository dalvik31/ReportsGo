package com.epacheco.reports.compose_reformat.ui.home.navigation

sealed class BottomNavHostScreens(val route: String) {
    data object ORDERS : BottomNavHostScreens("orders_tab")
    data object CLIENTS : BottomNavHostScreens("clients_tab")
    data object PRODUCTS : BottomNavHostScreens("products_tab")
    data object PROFILE : BottomNavHostScreens("profile_tab")
    data object FINANCES : BottomNavHostScreens("finances_tab")
}
