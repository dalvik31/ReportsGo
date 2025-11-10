package com.epacheco.reports.compose_reformat.ui.recovery_password


data class RecoveryPasswordUiState(
    val inputEmail: String = "",
    val enabledButton: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successOperationMsg: Int? = null
)