package com.epacheco.reports.compose_reformat.domain.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import javax.inject.Inject

class GetClientByNameUseCase @Inject constructor(private val clientsRepository: ClientsRepository) {
    suspend operator fun invoke(clientName: String? = null): Resource<List<Client>> {
        return clientsRepository.getClientByName(clientName)
    }
}