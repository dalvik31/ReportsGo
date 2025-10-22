package com.epacheco.reports.compose_reformat.repository.sales

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.model.sales.SaleDetail
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import java.io.File

interface SalesRepository {
    suspend fun createSale(sale: SaleDetail): Resource<Any>
    fun getSalesReference(): DatabaseReference

}