package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend operator fun invoke(): Resource<List<Product>> {
        return productsRepository.getProducts()
    }
}