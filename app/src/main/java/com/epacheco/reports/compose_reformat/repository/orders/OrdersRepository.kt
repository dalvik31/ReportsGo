package com.epacheco.reports.compose_reformat.repository.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.google.firebase.database.DatabaseReference

interface OrdersRepository {
    //Orders
    suspend fun getOrders(mainOrderId: String): Resource<List<Order>>
    suspend fun getOrderById(mainOrderId: String, orderId: String): Resource<Order?>
    suspend fun createOrder(order: Order): Resource<Any>
    suspend fun deleteOrder(orderId: String, mainOrderId: String): Resource<Any>
    suspend fun updateOrder(order: Order): Resource<Any>
    suspend fun updateStatusOrder(
        orderId: String,
        mainOrderId: String,
        orderBuy: Boolean,
        locationLat: Double?,
        locationLong: Double?,
        address: String?
    ): Resource<Any>

    suspend fun moveOrders(order: List<Order>, orderMainId: String): Resource<Any>


    //Main orders
    suspend fun getMainOrders(): Resource<List<OrderMain>>
    suspend fun deleteMainOrder(mainOrderId: String): Resource<Any>
    suspend fun createMainOrder(
        newOrderMain: OrderMain,
        addCreateRestriction: Boolean
    ): Resource<Any>

    suspend fun updateStatusMainOrder(orderId: String, orderStatus: OrderStatus): Resource<Any>
    fun getOrdersReference(): DatabaseReference
}