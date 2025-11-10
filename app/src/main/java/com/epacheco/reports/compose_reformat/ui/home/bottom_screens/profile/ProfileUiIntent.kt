package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import java.io.File

sealed class ProfileUiIntent {
    data object Logout : ProfileUiIntent()
    data class UploadProfileImage(val imageFile: File?) : ProfileUiIntent()
    data object Error : ProfileUiIntent()
}

