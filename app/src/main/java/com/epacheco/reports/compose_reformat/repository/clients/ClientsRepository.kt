package com.epacheco.reports.compose_reformat.repository.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.google.firebase.database.DatabaseReference

interface ClientsRepository {
    suspend fun getClients(paramName: String = ""): Resource<List<Client>>
    fun getClientsReference(): DatabaseReference
}