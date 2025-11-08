package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class OrderMainDeleteUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(orderId: String): Resource<Any> {
        return ordersRepository.deleteMainOrder(orderId)
    }
}