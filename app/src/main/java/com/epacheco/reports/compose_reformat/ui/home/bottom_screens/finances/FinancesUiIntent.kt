package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season

sealed class FinancesUiIntent {
    data object LoadFinancesItems : FinancesUiIntent()
    data class SetInitialDate(val initialDate: Long) : FinancesUiIntent()
    data class SetFinalDate(val finalDate: Long) : FinancesUiIntent()

}

