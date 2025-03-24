package com.epacheco.reports.compose_reformat.model.orders

data class Order(
    val orderId: String = "",
    val nameOrder: String = "",
    val orderDate: String = "",
    val orderStatus: OrderStatus = OrderStatus.IN_PROGRESS
)
