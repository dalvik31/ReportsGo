package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUpdateImgProfileUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUploadImgProfileUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLogoutUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseUserLogoutUseCase: FirebaseUserLogoutUseCase,
    private val firebaseGetUserUseCase: FirebaseGetUserUseCase,
    private val firebaseUploadImgProfileUseCase: FirebaseUploadImgProfileUseCase,
    private val updateImgProfileUseCase: FirebaseUpdateImgProfileUseCase,
    private val app: ReportsApp
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
            is ProfileUiIntent.UploadProfileImage -> uploadProfileImage(intent.imageUri)
        }
    }

    private fun getProfile() = viewModelScope.launch {
        loading(true)
        when (val profileResponse = firebaseGetUserUseCase()) {
            is Resource.Failure -> {
                _uiState.update { it.copy(errorMessage = profileResponse.exception.message) }
            }

            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        userProfile = profileResponse.result,
                        imgUser = profileResponse.result.photoUrl
                    )
                }
            }
        }
        loading(false)
    }

    private fun updateProfile(url: Uri) {
        viewModelScope.launch {
            loading(true)
            when (val updateImgProfileResponse = updateImgProfileUseCase(url)) {
                is Resource.Failure ->
                    setErrorMsg(updateImgProfileResponse.exception.message)
                is Resource.Success -> {
                    getProfile()
                }
            }
            loading(false)
        }
    }

    private fun doLogout() {
        viewModelScope.launch {
            when (val logoutResponse = firebaseUserLogoutUseCase()) {
                is Resource.Failure -> {
                    _uiState.update {
                        it.copy(errorMessage = logoutResponse.exception.message)
                    }
                }

                is Resource.Success -> {
                    _effectFlow.emit(ProfileUiEffect.NavigateToLogin)
                }
            }
        }
    }


    private fun uploadProfileImage(imageUri: Uri?) {
        viewModelScope.launch {
            imageUri?.let {
                loading(true)
                when (val uploadImageResponse = firebaseUploadImgProfileUseCase(imageUri)) {
                    is Resource.Failure ->
                        setErrorMsg(uploadImageResponse.exception.message)

                    is Resource.Success -> {
                        updateProfile(uploadImageResponse.result)
                    }
                }
                loading(false)
            }
        }
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }
}