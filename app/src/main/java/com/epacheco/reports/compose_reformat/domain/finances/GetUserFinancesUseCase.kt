package com.epacheco.reports.compose_reformat.domain.finances

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.repository.finances.FinancesRepository
import javax.inject.Inject

class GetUserFinancesUseCase @Inject constructor(private val financesRepository: FinancesRepository) {
    suspend operator fun invoke(initialDate: Long, finalDate: Long): Resource<List<Sale>> {
        return financesRepository.getFinances(initialDate = initialDate, finalDate = finalDate)
    }
}