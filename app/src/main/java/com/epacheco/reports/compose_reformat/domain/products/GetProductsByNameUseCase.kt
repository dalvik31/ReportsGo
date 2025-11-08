package com.epacheco.reports.compose_reformat.domain.products

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import javax.inject.Inject

class GetProductsByNameUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(productName: String?): Resource<List<Product>> {
        return productsRepository.getProductsByName(productName)
    }
}