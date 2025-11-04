package com.epacheco.reports.compose_reformat.ui.home.navigation

import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import kotlinx.serialization.Serializable

@Serializable
sealed interface BottomHomeRoutes {

    @Serializable
    data class MainOrdersBottomHomeRoute(
        val route: String = "Orders",
        val idClient: String? = null
    ) : BottomHomeRoutes

    @Serializable
    data class DetailMainOrdersBottomHomeRoute(
        val idOrderMain: String,
        val orderSeason: Season?,
        val nameOrderMain: String,
        val clientId: String? = null
    ) :
        BottomHomeRoutes

    @Serializable
    data class CreateOrderBottomHomeRoute(
        val orderToEdit: String?,
        val idOrderMain: String?,
        val orderSeason: Season?,
        val clientId: String? = null
    ) : BottomHomeRoutes

    @Serializable
    data class ClientDetailInformation(val idClient: String?) : BottomHomeRoutes {

    }

    @Serializable
    data class ClientOrdersBottomHomeRoute(val route: String = "Clients", val idClient: String?) :
        BottomHomeRoutes

    @Serializable
    data class ClientDetailBottomHomeRoute(val idClient: String?) : BottomHomeRoutes

    @Serializable
    data class ClientCreateNewBottomHomeRoute(val idClient: String?) : BottomHomeRoutes

    @Serializable
    data class ClientBottomHomeRoute(val isSelectableClient: Boolean = false) : BottomHomeRoutes

    @Serializable
    data class ProductBottomHomeRoute(val isSelectableProduct: Boolean = false) : BottomHomeRoutes

    @Serializable
    data class CreateProductBottomHomeRoute(val productId: String?) : BottomHomeRoutes

    @Serializable
    data object ProfileBottomHomeRoute : BottomHomeRoutes

    @Serializable
    data class SaleBottomHomeRoute(val idClient: String? = null) : BottomHomeRoutes

    @Serializable
    data object FinancesBottomHomeRoute : BottomHomeRoutes
}

