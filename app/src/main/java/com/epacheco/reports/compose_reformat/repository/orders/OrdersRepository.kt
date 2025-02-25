package com.epacheco.reports.compose_reformat.repository.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.google.firebase.database.DatabaseReference

interface OrdersRepository {
    suspend fun getOrders(): Resource<List<Order>>
    fun getOrdersReference(): DatabaseReference
}