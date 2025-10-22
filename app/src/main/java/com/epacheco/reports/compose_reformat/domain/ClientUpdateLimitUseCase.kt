package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import javax.inject.Inject

class ClientUpdateLimitUseCase @Inject constructor(private val clientsRepository: ClientsRepository) {
    suspend operator fun invoke(
        clientId: String,
        newLimit: Double,
        newLimitUsed: Double
    ): Resource<Any> {
        return clientsRepository.updateClientLimit(
            clientId = clientId,
            newLimit = newLimit,
            newLimitUsed = newLimitUsed
        )
    }
}