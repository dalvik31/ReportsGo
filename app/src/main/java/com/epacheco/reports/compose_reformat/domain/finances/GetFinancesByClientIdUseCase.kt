package com.epacheco.reports.compose_reformat.domain.finances

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.repository.finances.FinancesRepository
import javax.inject.Inject

class GetFinancesByClientIdUseCase @Inject constructor(private val financesRepository: FinancesRepository) {
    suspend operator fun invoke(clientId: String): Resource<List<Sale>> {
        return financesRepository.getFinancesByClientId(clientId)
    }
}