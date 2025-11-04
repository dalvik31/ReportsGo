package com.epacheco.reports.compose_reformat.ui.home.navigation

import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.home.navigation.BottomHomeRoutes.MainOrdersBottomHomeRoute

data class BottomHomeNavigationItem(
    val route: String = "",
    val label: Int = R.string.app_name,
    val icon: Int = R.drawable.ic_vector_order,
    val bottomHomeRoutes: BottomHomeRoutes = BottomHomeRoutes.MainOrdersBottomHomeRoute()
) {
    fun bottomNavigationItems(): List<BottomHomeNavigationItem> {
        return listOf(
            BottomHomeNavigationItem(
                route = MainOrdersBottomHomeRoute.javaClass.canonicalName.substringBefore(".Companion"),
                label = R.string.tab_order_option,
                icon = R.drawable.ic_vector_order,
                bottomHomeRoutes = BottomHomeRoutes.MainOrdersBottomHomeRoute()
            ),
            BottomHomeNavigationItem(
                route = BottomHomeRoutes.ClientBottomHomeRoute.javaClass.canonicalName.substringBefore(
                    ".Companion"
                ),
                label = R.string.tab_clients_option,
                icon = R.drawable.ic_vector_clients,
                bottomHomeRoutes = BottomHomeRoutes.ClientBottomHomeRoute()
            ),
            BottomHomeNavigationItem(
                route = BottomHomeRoutes.ProductBottomHomeRoute.javaClass.canonicalName.substringBefore(
                    ".Companion"
                ),
                label = R.string.tab_products_option,
                icon = R.drawable.ic_vector_products_red,
                bottomHomeRoutes = BottomHomeRoutes.ProductBottomHomeRoute()
            ),


            BottomHomeNavigationItem(
                route = BottomHomeRoutes.SaleBottomHomeRoute.javaClass.canonicalName.substringBefore(
                    ".Companion"
                ),
                label = R.string.tab_sales_option,
                icon = R.drawable.ic_vector_sale,
                bottomHomeRoutes = BottomHomeRoutes.SaleBottomHomeRoute()
            ),
            /*BottomHomeNavigationItem(
                label = R.string.tab_profile_option,
                icon = R.drawable.ic_vector_account,
                bottomHomeRoutes = BottomHomeRoutes.ProfileBottomHomeRoute
            )*/
        )
    }
}