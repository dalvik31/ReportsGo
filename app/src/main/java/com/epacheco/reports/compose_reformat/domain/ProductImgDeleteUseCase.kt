package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import javax.inject.Inject

class ProductImgDeleteUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(imagName: String?): Resource<Any> {
        return productsRepository.deleteImgProduct(imagName)
    }
}