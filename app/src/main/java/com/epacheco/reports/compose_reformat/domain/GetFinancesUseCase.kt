package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.repository.finances.FinancesRepository
import javax.inject.Inject

class GetFinancesUseCase @Inject constructor(private val financesRepository: FinancesRepository) {
    suspend operator fun invoke(): Resource<List<Sale>> {
        return financesRepository.getFinances()
    }
}