package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class CreateMainOrderUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(
        orderMain: OrderMain,
        addCreateRestriction: Boolean = false
    ): Resource<Any> {
        return ordersRepository.createMainOrder(orderMain, addCreateRestriction)
    }
}