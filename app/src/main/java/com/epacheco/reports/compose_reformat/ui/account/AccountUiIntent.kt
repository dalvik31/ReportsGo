package com.epacheco.reports.compose_reformat.ui.account

import androidx.credentials.GetCredentialResponse

sealed class AccountUiIntent {
    data object SignIn : AccountUiIntent()
    data class GoogleSignIn(val getCredentialResponse: GetCredentialResponse) :
        AccountUiIntent()

    data object SignUp : AccountUiIntent()
    data object ChangePassword : AccountUiIntent()
    data object HideMsgError : AccountUiIntent()
}

