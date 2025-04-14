package com.epacheco.reports.compose_reformat.model.orders

data class OrderMain(
    val orderId: String = "",
    val nameOrder: String = "",
    @Deprecated(message = "orderId is the new value") val dateOrder: String = "",
    val orderDate: String = "",
    val orderStatus: OrderStatus = OrderStatus.IN_PROGRESS,
    val orderSeason: Season? = null
)
