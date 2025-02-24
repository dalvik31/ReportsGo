package com.epacheco.reports.compose_reformat.repository.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order

interface OrdersRepository {
    suspend fun getOrders(): Resource<List<Order>>
}