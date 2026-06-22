package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import com.epacheco.reports.compose_reformat.model.sales.Sale

data class FinancesUiState(
    val financesList: List<Sale> = emptyList(),
    val initialDate: String = System.currentTimeMillis().toString(),
    val finalDate: String = System.currentTimeMillis().toString(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)