package com.epacheco.reports.compose_reformat.repository.products.supabase

import com.epacheco.reports.compose_reformat.model.products.supabase.Product
import com.epacheco.reports.compose_reformat.model.products.supabase.ProductDto

interface ProductRepository {
    suspend fun createProduct(product: Product): Boolean
    suspend fun getProducts(): List<ProductDto>?
    suspend fun getProduct(id: String): ProductDto
    suspend fun deleteProduct(id: String)
    suspend fun updateProduct(
        id: String, name: String, price: Double, imageName: String, imageFile: ByteArray
    )

}