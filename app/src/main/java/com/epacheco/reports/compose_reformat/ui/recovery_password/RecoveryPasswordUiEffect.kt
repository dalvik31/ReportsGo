package com.epacheco.reports.compose_reformat.ui.recovery_password

sealed class RecoveryPasswordUiEffect {
    data object NavigateBack : RecoveryPasswordUiEffect()
}
