package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker.PickerPictureDialog
import com.epacheco.reports.compose_reformat.model.user.UserInfoProfile
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(),
    onNavigateToLogin: () -> Unit,
) {

    val uiState by profileViewModel.uiState.collectAsState()

    var showCloseSessionDialog by remember { mutableStateOf(false) }
    var showProfilePictureDialog by remember { mutableStateOf(false) }

    LaunchedEffect(profileViewModel) {
        profileViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                ProfileUiEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }

            }
        }
    }

    ProfileView(
        userInfoProfile = uiState.userProfile,
        onLogoutClick = {
            profileViewModel.handleIntent(ProfileUiIntent.Logout)

        },
        onUpdateProfilePictureClicked = {
            showProfilePictureDialog = true
        },
        onVerifyEmailClick = {
            profileViewModel.handleIntent(ProfileUiIntent.SendEmailVerification)
        }
    )
    if (uiState.isLoading) {
        Loader(false)
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                profileViewModel.handleIntent(ProfileUiIntent.Error)
            })
    }

    uiState.successMsg?.let { successMsg ->
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_ok,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = successMsg,
            onConfirmation = {
                profileViewModel.handleIntent(ProfileUiIntent.Error)
                profileViewModel.handleIntent(ProfileUiIntent.Logout)
            })
    }


    if (showProfilePictureDialog) {
        PickerPictureDialog(
            onDismissRequest = { showProfilePictureDialog = false },
            onImageSelected = {
                profileViewModel.handleIntent(ProfileUiIntent.UploadProfileImage(it))
            })
    }


}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ReportsGoTheme(darkTheme = false) {
        ProfileView(UserInfoProfile("1", "correo@gmail.com", null, null, null, null), {}, {}, {})
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun ProfileScreenDarkPreview() {
    ReportsGoTheme(darkTheme = true) {
        ProfileView(UserInfoProfile("1", "correo@gmail.com", null, null, null, null), {}, {}, {})

    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun NewProfileScreenUserNullPreview() {
    ReportsGoTheme(darkTheme = false) {
        ProfileView(userInfoProfile = null, {}, {}, {})
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
fun NewProfileScreenUserNullDarkPreview() {
    ReportsGoTheme(darkTheme = true) {
        ProfileView(userInfoProfile = null, {}, {}, {})
    }
}
