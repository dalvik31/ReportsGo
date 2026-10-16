package com.epacheco.reports.compose_reformat.domain.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import javax.inject.Inject

class UpdateBalanceClientUseCase @Inject constructor(private val clientsRepository: ClientsRepository) {
    suspend operator fun invoke(
        clientId: String,
        newDebt: Double
    ): Resource<Any> {
        return clientsRepository.updateClientBalance(
            clientId = clientId,
            newDebt = newDebt,
        )
    }
}