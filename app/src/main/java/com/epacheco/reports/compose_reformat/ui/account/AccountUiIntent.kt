package com.epacheco.reports.compose_reformat.ui.account

sealed class AccountUiIntent {
    data object SignIn : AccountUiIntent()  // SignIn intent
    data object SignUp : AccountUiIntent()  // SignUp intent
    data object ChangePassword : AccountUiIntent()  // Change password intent
    data object HideMsgError : AccountUiIntent()  // Hide error
    data object GetProfile : AccountUiIntent()  // Get Profile
}

