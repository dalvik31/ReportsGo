package com.epacheco.reports.compose_reformat.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.user.GetUserUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.CurrentUserUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.CurrentUserUseCase.*
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val currentUserUseCase: CurrentUserUseCase
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<SplashUiEffect>()
    val effectFlow: SharedFlow<SplashUiEffect> = _effectFlow

    fun handleIntent(intent: SplashUiIntent) {
        when (intent) {
            is SplashUiIntent.CheckUserStatus -> checkUserStatus()
        }
    }

    private fun checkUserStatus() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        when (currentUserUseCase.execute(Input())) {
            is Output.Failure -> _effectFlow.emit(SplashUiEffect.NavigateToLogin)
            is Output.Success -> _effectFlow.emit(SplashUiEffect.NavigateToHome)
        }
        _uiState.update { it.copy(isLoading = false) }
    }
}