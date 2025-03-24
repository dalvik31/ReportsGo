package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class UpdateStatusOrderUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(orderId: String, orderStatus: OrderStatus): Resource<Boolean> {
        return ordersRepository.updateStatusOrder(orderId, orderStatus)
    }
}