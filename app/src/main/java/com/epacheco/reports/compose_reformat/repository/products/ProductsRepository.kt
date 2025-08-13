package com.epacheco.reports.compose_reformat.repository.products

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.google.firebase.database.DatabaseReference

interface ProductsRepository {
    suspend fun getProductsByName(productName: String?): Resource<List<Product>>
    fun getProductsReference(): DatabaseReference

}