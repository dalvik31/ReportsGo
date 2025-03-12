package com.epacheco.reports.compose_reformat.ui.splash

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val firebaseGetUserUseCase: FirebaseGetUserUseCase) :
    BaseViewModel() {

    // State to represent the loading state of the splash screen
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState

    // SharedFlow for effects like navigation
    private val _effectFlow = MutableSharedFlow<SplashUiEffect>()
    val effectFlow: SharedFlow<SplashUiEffect> = _effectFlow

    // Handle the intents coming from the SplashScreen
    fun handleIntent(intent: SplashUiIntent) {
        when (intent) {
            is SplashUiIntent.CheckUserStatus -> checkUserStatus()
        }
    }

    // Check if the user is logged in and decide the navigation route
    private fun checkUserStatus() = viewModelScope.launch {
        loading(true)

        when (firebaseGetUserUseCase()) {
            is Resource.Failure -> _effectFlow.emit(SplashUiEffect.NavigateToLogin)
            is Resource.Success -> {
                _effectFlow.emit(SplashUiEffect.NavigateToHome)
            }
        }
        loading(true)
    }


    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }
}