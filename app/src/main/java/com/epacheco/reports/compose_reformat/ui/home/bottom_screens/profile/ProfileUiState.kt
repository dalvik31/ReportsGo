package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.net.Uri
import com.google.firebase.auth.FirebaseUser


data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMsg: String? = null,
    val userProfile: FirebaseUser? = null,
    val signInMethod: String? = null,
    val imgUser: Uri? = null
)