package com.epacheco.reports.compose_reformat.repository.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.google.firebase.database.DatabaseReference

interface ClientsRepository {
    suspend fun getClientByName(clientName: String?): Resource<List<Client>>
    suspend fun getClient(id: String): Resource<Client>
    suspend fun updateClientLimit(
        clientId: String,
        newLimit: Double,
        newLimitUsed: Double
    ): Resource<Any>

    suspend fun updateClientDebt(
        clientId: String,
        newDebt: Double,
    ): Resource<Any>

    suspend fun updateClient(client: Client): Resource<Any>
    suspend fun createClient(client: Client): Resource<Any>
    suspend fun deleteClient(clientId: String): Resource<Any>

    fun getClientsReference(): DatabaseReference
}