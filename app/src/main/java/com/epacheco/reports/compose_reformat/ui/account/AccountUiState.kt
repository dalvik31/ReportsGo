package com.epacheco.reports.compose_reformat.ui.account


data class AccountUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)