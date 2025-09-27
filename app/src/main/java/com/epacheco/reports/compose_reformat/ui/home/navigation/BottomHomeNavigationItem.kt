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
            /**
             * Pending 07/05/2025
             * */
            /*BottomHomeNavigationItem(
                label = R.string.tab_clients_option,
                icon = R.drawable.ic_vector_clients,
                bottomHomeRoutes = BottomHomeRoutes.ClientBottomHomeRoute
            ),*/
            BottomHomeNavigationItem(
                label = R.string.tab_products_option,
                icon = R.drawable.ic_vector_products_red,
                bottomHomeRoutes = BottomHomeRoutes.ProductBottomHomeRoute
            ),

            /**
             * Pending 07/05/2025
             * */
            /*BottomHomeNavigationItem(
                label = R.string.tab_finances_option,
                icon = R.drawable.ic_vector_activity,
                bottomHomeRoutes = BottomHomeRoutes.FinanceBottomHomeRoute
            ),*/
            BottomHomeNavigationItem(
                label = R.string.tab_profile_option,
                icon = R.drawable.ic_vector_account,
                bottomHomeRoutes = BottomHomeRoutes.ProfileBottomHomeRoute
            )
        )
    }
}