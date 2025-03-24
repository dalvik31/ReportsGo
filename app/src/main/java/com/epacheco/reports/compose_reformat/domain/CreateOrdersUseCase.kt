package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class CreateOrdersUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(order: Order, addCreateRestriction: Boolean = false): Resource<Boolean> {
        return ordersRepository.createOrder(order, addCreateRestriction)
    }
}