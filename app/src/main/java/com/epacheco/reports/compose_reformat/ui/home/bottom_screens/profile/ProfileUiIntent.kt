package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

sealed class ProfileUiIntent {
    data object Logout : ProfileUiIntent()  // Logout intent
    data object Error : ProfileUiIntent()  // Show error
}

