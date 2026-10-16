package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

sealed class FinancesUiIntent {
    data object LoadFinancesItems : FinancesUiIntent()
    data class SetInitialDate(val initialDate: String) : FinancesUiIntent()
    data class SetFinalDate(val finalDate: String) : FinancesUiIntent()

}

