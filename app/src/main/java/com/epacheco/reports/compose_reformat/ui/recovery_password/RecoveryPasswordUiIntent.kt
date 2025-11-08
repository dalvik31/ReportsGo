package com.epacheco.reports.compose_reformat.ui.recovery_password

sealed class RecoveryPasswordUiIntent {
    data object RecoveryPassword : RecoveryPasswordUiIntent()
    data object HideDialogs : RecoveryPasswordUiIntent()

}

