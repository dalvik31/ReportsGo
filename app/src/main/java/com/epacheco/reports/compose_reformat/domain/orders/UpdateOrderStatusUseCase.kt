package com.epacheco.reports.compose_reformat.domain.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(private val ordersRepository: OrdersRepository) {
    suspend operator fun invoke(
        orderId: String,
        mainOrderId: String,
        orderBuy: Boolean,
        latitude: Double? = null,
        longitude: Double? = null,
        address: String? = null
    ): Resource<Any> {
        return ordersRepository.updateStatusOrder(
            orderId,
            mainOrderId = mainOrderId,
            orderBuy = orderBuy,
            locationLat = latitude,
            locationLong = longitude,
            address = address
        )
    }
}