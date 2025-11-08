package com.epacheco.reports.compose_reformat.ui.account

sealed class AccountUiEffect {
    data object NavigateToHome : AccountUiEffect()
    data object NavigateToPassword : AccountUiEffect()
}
