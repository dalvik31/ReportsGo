package com.epacheco.reports.compose_reformat.ui.splash

sealed class SplashUiEffect {
    data object NavigateToLogin : SplashUiEffect() //Go Login
    data object NavigateToHome : SplashUiEffect()  //Go Home
}
