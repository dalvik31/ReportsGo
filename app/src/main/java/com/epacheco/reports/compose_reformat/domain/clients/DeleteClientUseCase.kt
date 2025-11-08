package com.epacheco.reports.compose_reformat.domain.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import javax.inject.Inject

class DeleteClientUseCase @Inject constructor(private val clientsRepository: ClientsRepository) {
    suspend operator fun invoke(
        clientId: String,
    ): Resource<Any> {
        return clientsRepository.deleteClient(
            clientId = clientId,
        )
    }
}