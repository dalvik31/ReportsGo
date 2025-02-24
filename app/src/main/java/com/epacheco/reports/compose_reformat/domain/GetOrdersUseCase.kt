package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(): Resource<List<Order>> {
        return ordersRepository.getOrders()
    }
}