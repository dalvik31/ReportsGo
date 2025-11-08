package com.epacheco.reports.compose_reformat.domain.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import javax.inject.Inject

class UpdateDebtClientUseCase @Inject constructor(private val clientsRepository: ClientsRepository) {
    suspend operator fun invoke(
        clientId: String,
        newDebt: Double
    ): Resource<Any> {
        return clientsRepository.updateClientDebt(
            clientId = clientId,
            newDebt = newDebt,
        )
    }
}