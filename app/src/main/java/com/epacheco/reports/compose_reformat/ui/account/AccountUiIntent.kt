package com.epacheco.reports.compose_reformat.ui.account

import androidx.credentials.GetCredentialRequest

sealed class AccountUiIntent {
    data object SignIn : AccountUiIntent()
    data class GoogleSignIn(val credentialRequest: GetCredentialRequest) :
        AccountUiIntent()

    data object SignUp : AccountUiIntent()
    data object ChangePassword : AccountUiIntent()
    data object HideMsgError : AccountUiIntent()
    //data object GetProfile : AccountUiIntent()
}

