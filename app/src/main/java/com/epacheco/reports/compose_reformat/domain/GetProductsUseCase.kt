package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.FirebaseCallBack
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(): Flow<List<Product>?> {
        return productsRepository.getProducts()
    }
}