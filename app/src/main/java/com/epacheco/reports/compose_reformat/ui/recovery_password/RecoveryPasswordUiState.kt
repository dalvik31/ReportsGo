package com.epacheco.reports.compose_reformat.ui.recovery_password


data class RecoveryPasswordUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null
)