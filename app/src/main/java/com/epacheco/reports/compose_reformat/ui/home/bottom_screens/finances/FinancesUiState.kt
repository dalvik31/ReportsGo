package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import com.epacheco.reports.compose_reformat.model.Finances.Sale

data class FinancesUiState(
    val financesList: List<Sale> = emptyList(),
    val initialDate: Long = System.currentTimeMillis(),
    val finalDate: Long = System.currentTimeMillis(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)