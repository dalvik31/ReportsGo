package com.epacheco.reports.compose_reformat.ui.splash

sealed class SplashUiIntent {
    data object CheckUserStatus : SplashUiIntent()  // Intent to check if the user is logged in or not
}
