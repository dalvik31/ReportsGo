package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.model.sales.SaleDetail
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import com.epacheco.reports.compose_reformat.repository.sales.SalesRepository
import javax.inject.Inject

class SaleCreateUseCase @Inject constructor(private val salesRepository: SalesRepository) {
    suspend operator fun invoke(
        saleDetail: SaleDetail
    ): Resource<Any> {
        return salesRepository.createSale(saleDetail)
    }
}