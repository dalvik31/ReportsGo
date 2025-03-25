package com.epacheco.reports.compose_reformat.model.orders


import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product

data class Order(
    val orderListId: String = "",
    val orderId: String = "",
    val nameOrder: String = "",
    val orderSize: String = "",
    val orderColor: String = "",
    val orderGender: String = "",
    val orderDescription: String = "",
    val orderStatus: OrderStatus = OrderStatus.IN_PROGRESS,
    val orderClient: Client = Client(),
    val orderProduct: Product = Product()
)