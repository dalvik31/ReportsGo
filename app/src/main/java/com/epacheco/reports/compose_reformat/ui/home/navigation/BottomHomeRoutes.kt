package com.epacheco.reports.compose_reformat.ui.home.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface BottomHomeRoutes {
    @Serializable
    data object MainOrdersBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data class CreateOrderBottomHomeRoute(val idOrderMain: String) : BottomHomeRoutes

    @Serializable
    data object ClientBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data object ProductBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data object ProfileBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data object FinanceBottomHomeRoute : BottomHomeRoutes
}

