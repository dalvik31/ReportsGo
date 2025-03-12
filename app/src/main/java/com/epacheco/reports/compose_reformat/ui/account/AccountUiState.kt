package com.epacheco.reports.compose_reformat.ui.account


data class AccountUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userInfoRetrieved: Boolean? = null
)