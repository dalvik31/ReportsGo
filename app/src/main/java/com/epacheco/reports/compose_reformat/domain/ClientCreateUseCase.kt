package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import javax.inject.Inject

class ClientCreateUseCase @Inject constructor(private val clientsRepository: ClientsRepository) {
    suspend operator fun invoke(
        client: Client,
    ): Resource<Any> {
        return clientsRepository.createClient(
            client = client,
        )
    }
}