package com.epacheco.reports.compose_reformat.ui.home.navigation

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import kotlinx.serialization.Serializable

@Serializable
sealed interface BottomHomeRoutes {
    @Serializable
    data object MainOrdersBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data class DetailMainOrdersBottomHomeRoute(
        val idOrderMain: String,
        val orderSeason: Season?
    ) :
        BottomHomeRoutes

    @Serializable
    data class CreateOrderBottomHomeRoute(
        val orderToEdit: Order?,
        val idOrderMain: String,
        val orderSeason: Season?
    ) : BottomHomeRoutes

    @Serializable
    data object ClientBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data object ProductBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data object ProfileBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data object FinanceBottomHomeRoute : BottomHomeRoutes
}

