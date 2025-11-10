package com.epacheco.reports.compose_reformat.ui.recovery_password

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.domain.user.RecoveryPasswordUserUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.extensions.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverPasswordViewModel @Inject constructor(
    private val recoveryPasswordUserUseCase: RecoveryPasswordUserUseCase,
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(RecoveryPasswordUiState())
    val uiState: StateFlow<RecoveryPasswordUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<RecoveryPasswordUiEffect>()
    val effectFlow: SharedFlow<RecoveryPasswordUiEffect> = _effectFlow


    fun handleIntent(intent: RecoveryPasswordUiIntent) {
        when (intent) {
            RecoveryPasswordUiIntent.RecoveryPassword -> fetchRecoveryPassword()
            RecoveryPasswordUiIntent.HideDialogs -> setErrorMsg()
        }
    }


    private fun fetchRecoveryPassword() =
        viewModelScope.launch {
            loading(true)
            when (val recoveryPasswordResponse =
                recoveryPasswordUserUseCase(email = _uiState.value.inputEmail)) {
                is Resource.Failure -> setErrorMsg(recoveryPasswordResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.recovery_password_success)
                }
            }
            loading(false)
        }


    fun onInputEmailChanged(inputEmail: String) {
        _uiState.update {
            it.copy(
                inputEmail = inputEmail,
                enabledButton = inputEmail.validateEmail()
            )
        }
    }


    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successOperationMsg = null) }
    }

    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

}