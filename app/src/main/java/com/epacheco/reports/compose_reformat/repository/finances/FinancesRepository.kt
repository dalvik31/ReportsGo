package com.epacheco.reports.compose_reformat.repository.finances

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.google.firebase.database.DatabaseReference

interface FinancesRepository {
    suspend fun getFinances(): Resource<List<Sale>>
    fun getFinancesReference(): DatabaseReference
}