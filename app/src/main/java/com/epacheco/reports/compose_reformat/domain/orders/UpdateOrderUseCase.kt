package com.epacheco.reports.compose_reformat.domain.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(
        order: Order
    ): Resource<Any> {
        return ordersRepository.updateOrder(order)
    }
}