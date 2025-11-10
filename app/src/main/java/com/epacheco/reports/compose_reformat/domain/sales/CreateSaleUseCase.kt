package com.epacheco.reports.compose_reformat.domain.sales

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.repository.sales.SalesRepository
import javax.inject.Inject

class CreateSaleUseCase @Inject constructor(private val salesRepository: SalesRepository) {
    suspend operator fun invoke(
        saleDetail: Sale
    ): Resource<Any> {
        return salesRepository.createSale(saleDetail)
    }
}