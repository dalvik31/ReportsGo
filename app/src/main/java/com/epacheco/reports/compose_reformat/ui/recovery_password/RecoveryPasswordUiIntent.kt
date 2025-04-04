package com.epacheco.reports.compose_reformat.ui.recovery_password

import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiIntent

sealed class RecoveryPasswordUiIntent {
    data object RecoveryPassword : RecoveryPasswordUiIntent()
    data object HideDialogs : RecoveryPasswordUiIntent()

}

