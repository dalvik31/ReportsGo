package com.epacheco.reports.compose_reformat.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    splashViewMode: SplashViewModel = hiltViewModel<SplashViewModel>(),
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val uiState by splashViewMode.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    //agreamos cambio en splash screen 
    LaunchedEffect(Unit) {
        splashViewMode.effectFlow.collectLatest { effect ->
            when (effect) {
                is SplashUiEffect.NavigateToLogin -> onNavigateToLogin()
                is SplashUiEffect.NavigateToHome -> onNavigateToHome()
            }
        }
        //Agreamos comentario para poder probar el git rebase
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(3000)
            splashViewMode.handleIntent(SplashUiIntent.CheckUserStatus)
        }
    }

    if (uiState.isLoading) {
        SplashView()
    }

}

@Preview
@Composable
fun RegisterScreenPreview() {
    ReportsGoTheme {
        SplashView()
    }
}