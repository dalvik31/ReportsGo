package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import javax.inject.Inject

class GetClientsUseCase @Inject constructor(private val clientsRepository: ClientsRepository) {
    suspend operator fun invoke(): Resource<List<Client>> {
        return clientsRepository.getClients()
    }
}