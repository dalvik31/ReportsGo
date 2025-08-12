package com.epacheco.reports.compose_reformat.repository.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.clients.ClientDetailCmps
import com.google.firebase.database.DatabaseReference

interface ClientsRepository {
    suspend fun getClients(paramName: String = ""): Resource<List<Client>>
    suspend fun getClient(id: String): Resource<Client>
    fun getClientsReference(): DatabaseReference
}