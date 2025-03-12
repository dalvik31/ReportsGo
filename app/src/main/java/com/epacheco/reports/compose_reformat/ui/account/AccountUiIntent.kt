package com.epacheco.reports.compose_reformat.ui.account

sealed class AccountUiIntent {
    data object SignIn : AccountUiIntent()  // SignIn intent
    data object SignUp : AccountUiIntent()  // SignUp intent
    data object Password : AccountUiIntent()  // Change password intent
    data object Error : AccountUiIntent()  // Show error
    data object GetProfile : AccountUiIntent()  // Get Profile
}

