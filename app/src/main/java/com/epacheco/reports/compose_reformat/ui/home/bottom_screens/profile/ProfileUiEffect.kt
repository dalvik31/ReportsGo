package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

sealed class ProfileUiEffect {
    data object NavigateToLogin : ProfileUiEffect()
}
