package com.epacheco.reports.compose_reformat.domain.products

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(
        productId: String
    ): Resource<Any> {
        return productsRepository.deleteProduct(productId)
    }
}