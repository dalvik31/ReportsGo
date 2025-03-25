package com.epacheco.reports.compose_reformat.repository.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.google.firebase.database.DatabaseReference

interface OrdersRepository {
    suspend fun createOrder(order: Order): Resource<Boolean>
    suspend fun getOrders(mainOrderId: String): Resource<List<Order>>
    suspend fun getMainOrders(): Resource<List<OrderMain>>
    suspend fun deleteMainOrder(orderId: String): Resource<Boolean>
    suspend fun createMainOrder(
        newOrderMain: OrderMain,
        addCreateRestriction: Boolean
    ): Resource<Boolean>

    suspend fun updateStatusMainOrder(orderId: String, orderStatus: OrderStatus): Resource<Boolean>
    fun getOrdersReference(): DatabaseReference
}