package com.epacheco.reports.compose_reformat.ui.home.navigation

import com.epacheco.reports.R

data class BottomNavigationItem(
    val label: Int = R.string.app_name,
    val icon: Int = R.drawable.ic_vector_order,
    val route: String = ""
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = R.string.tab_order_option,
                icon = R.drawable.ic_vector_order,
                route = BottomNavHostScreens.ORDERS.route
            ),
            BottomNavigationItem(
                label = R.string.tab_clients_option,
                icon = R.drawable.ic_vector_clients,
                route = BottomNavHostScreens.CLIENTS.route
            ),
            BottomNavigationItem(
                label = R.string.tab_products_option,
                icon = R.drawable.ic_vector_products_red,
                route = BottomNavHostScreens.PRODUCTS.route
            ),
            BottomNavigationItem(
                label = R.string.tab_finances_option,
                icon = R.drawable.ic_vector_activity,
                route = BottomNavHostScreens.FINANCES.route
            ),
            BottomNavigationItem(
                label = R.string.tab_profile_option,
                icon = R.drawable.ic_vector_account,
                route = BottomNavHostScreens.PROFILE.route
            )
        )
    }
}