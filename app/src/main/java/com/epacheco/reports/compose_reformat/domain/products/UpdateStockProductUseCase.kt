package com.epacheco.reports.compose_reformat.domain.products

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import javax.inject.Inject

class UpdateStockProductUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(
        productId: String,
        newStock: Int
    ): Resource<Any> {
        return productsRepository.updateStockProduct(productId = productId, newStock = newStock)
    }
}