package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLogoutUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseUserLogoutUseCase: FirebaseUserLogoutUseCase,
    private val firebaseGetUserUseCase: FirebaseGetUserUseCase,
) :
    BaseViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<ProfileUiEffect>()
    val effectFlow: SharedFlow<ProfileUiEffect> = _effectFlow

    init {
        getProfile()
    }

    fun handleIntent(intent: ProfileUiIntent) {
        when (intent) {
            ProfileUiIntent.Logout -> doLogout()
            ProfileUiIntent.Error -> setErrorMsg()
        }
    }

    private fun getProfile() = viewModelScope.launch {
        loading(true)
        when (val profileResponse = firebaseGetUserUseCase()) {
            is Resource.Failure -> {
                _uiState.value =
                    _uiState.value.copy(errorMessage = profileResponse.exception.message)
            }

            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(userProfile = profileResponse.result)
            }
        }
        loading(false)
    }


    private fun doLogout() {
        viewModelScope.launch {
            when (val logoutResponse = firebaseUserLogoutUseCase()) {
                is Resource.Failure -> {
                    _uiState.value =
                        _uiState.value.copy(errorMessage = logoutResponse.exception.message)
                }

                is Resource.Success -> {
                    _effectFlow.emit(ProfileUiEffect.NavigateToLogin)
                }
            }
        }
    }


    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }
}