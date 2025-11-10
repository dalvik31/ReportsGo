package com.epacheco.reports.compose_reformat.repository.sales

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.google.firebase.database.DatabaseReference

interface SalesRepository {
    suspend fun createSale(sale: Sale): Resource<Any>
    fun getSalesReference(): DatabaseReference

}