package com.epacheco.reports.compose_reformat.ui.account

import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PrepareGetCredentialResponse

sealed class AccountUiIntent {
    data object SignIn : AccountUiIntent()
    data class GoogleSignIn(val getCredentialResponse: GetCredentialResponse) :
        AccountUiIntent()

    data object SignUp : AccountUiIntent()
    data object ChangePassword : AccountUiIntent()
    data object HideMsgError : AccountUiIntent()
    //data object GetProfile : AccountUiIntent()
}

