package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.net.Uri
import com.epacheco.reports.compose_reformat.model.user.UserInfoProfile


data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMsg: String? = null,
    val userProfile: UserInfoProfile? = null,
)