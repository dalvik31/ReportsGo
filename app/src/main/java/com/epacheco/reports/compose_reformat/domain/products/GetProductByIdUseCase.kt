package com.epacheco.reports.compose_reformat.domain.products

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(productId: String?): Resource<Product> {
        return productsRepository.getProductsById(productId)
    }
}