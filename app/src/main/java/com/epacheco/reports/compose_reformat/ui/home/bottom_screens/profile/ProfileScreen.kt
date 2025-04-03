package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>(),
    onNavigateToRegister: () -> Unit,
) {

    val uiState by profileViewModel.uiState.collectAsState()

    LaunchedEffect(profileViewModel) {
        profileViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                ProfileUiEffect.NavigateToLogin -> {
                    onNavigateToRegister()
                }
            }
        }
    }

    ProfileView(
        firebaseUser = uiState.userProfile,
        loading = uiState.isLoading,
        onLogoutClicked = {
            profileViewModel.handleIntent(ProfileUiIntent.Logout)
        })

    // Loading Overlay
    if (uiState.isLoading) {
        Loader(false, stringResource(R.string.search_profile))
    }

    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsErrorDialog(
            dialogSubTitle = msgError,
            onConfirmation = {
                profileViewModel.handleIntent(ProfileUiIntent.Error)
            })
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ReportsGoTheme {
        ProfileView(null, loading = true)
    }

}