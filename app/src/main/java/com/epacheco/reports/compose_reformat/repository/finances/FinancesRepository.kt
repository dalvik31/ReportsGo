package com.epacheco.reports.compose_reformat.repository.finances

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.google.firebase.database.DatabaseReference

interface FinancesRepository {
    suspend fun getFinances(initialDate: Long, finalDate: Long): Resource<List<Sale>>
    suspend fun getFinancesByClientId(clientId: String): Resource<List<Sale>>
    fun getFinancesReference(): DatabaseReference
}