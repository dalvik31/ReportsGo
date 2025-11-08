package com.epacheco.reports.compose_reformat.ui.splash

sealed class SplashUiIntent {
    data object CheckUserStatus : SplashUiIntent()
}
