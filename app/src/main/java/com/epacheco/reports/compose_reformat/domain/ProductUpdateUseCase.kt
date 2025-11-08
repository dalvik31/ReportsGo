package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import javax.inject.Inject

class ProductUpdateUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(
        product: Product
    ): Resource<Any> {
        return productsRepository.updateProduct(product)
    }
}