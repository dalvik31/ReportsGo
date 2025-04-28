package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.net.Uri

sealed class ProfileUiIntent {
    data object Logout : ProfileUiIntent()
    data class UploadProfileImage(val imageUri: Uri?) : ProfileUiIntent()
    data object Error : ProfileUiIntent()
}

