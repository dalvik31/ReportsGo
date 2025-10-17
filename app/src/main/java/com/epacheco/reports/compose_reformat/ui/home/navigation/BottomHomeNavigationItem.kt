package com.epacheco.reports.compose_reformat.ui.home.navigation

import com.epacheco.reports.R

data class BottomHomeNavigationItem(
    val label: Int = R.string.app_name,
    val icon: Int = R.drawable.ic_vector_order,
    val bottomHomeRoutes: BottomHomeRoutes = BottomHomeRoutes.MainOrdersBottomHomeRoute
) {
    fun bottomNavigationItems(): List<BottomHomeNavigationItem> {
        return listOf(
            BottomHomeNavigationItem(
                label = R.string.tab_order_option,
                icon = R.drawable.ic_vector_order,
                bottomHomeRoutes = BottomHomeRoutes.MainOrdersBottomHomeRoute
            ),
            BottomHomeNavigationItem(
                label = R.string.tab_clients_option,
                icon = R.drawable.ic_vector_clients,
                bottomHomeRoutes = BottomHomeRoutes.ClientBottomHomeRoute()
            ),
            BottomHomeNavigationItem(
                label = R.string.tab_products_option,
                icon = R.drawable.ic_vector_products_red,
                bottomHomeRoutes = BottomHomeRoutes.ProductBottomHomeRoute()
            ),

            BottomHomeNavigationItem(
                label = R.string.tab_sales_option,
                icon = R.drawable.ic_vector_sale,
                bottomHomeRoutes = BottomHomeRoutes.SaleBottomHomeRoute
            ),
            /*BottomHomeNavigationItem(
                label = R.string.tab_profile_option,
                icon = R.drawable.ic_vector_account,
                bottomHomeRoutes = BottomHomeRoutes.ProfileBottomHomeRoute
            )*/
        )
    }
}