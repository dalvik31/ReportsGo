package com.epacheco.reports.compose_reformat.ui.account

sealed class AccountUiIntent {
    data object SignIn : AccountUiIntent()
    data object SignUp : AccountUiIntent()
    data object ChangePassword : AccountUiIntent()
    data object HideMsgError : AccountUiIntent()
    data object GetProfile : AccountUiIntent()
}

