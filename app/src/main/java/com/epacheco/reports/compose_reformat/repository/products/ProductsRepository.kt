package com.epacheco.reports.compose_reformat.repository.products

import com.epacheco.reports.compose_reformat.firebase.FirebaseCallBack
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts():  Flow<List<Product>>
    suspend fun getProducts(firebaseCallBack: FirebaseCallBack)
    fun getUser(): Flow<List<Product>?>
    fun getProductsReference(): DatabaseReference

    suspend fun getAllProducts(): Flow<Result<List<Product>>>
}