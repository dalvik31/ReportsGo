package com.epacheco.reports.compose_reformat.ui.recovery_password

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.FirebaseRecoveryPasswordUserUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.extensions.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverPasswordViewModel @Inject constructor(
    private val recoveryPasswordUserUseCase: FirebaseRecoveryPasswordUserUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _enabledButton = MutableStateFlow(false)
    val enabledButton: StateFlow<Boolean> = _enabledButton
    private val _inputEmail = MutableStateFlow("")
    val inputEmail: StateFlow<String> = _inputEmail


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
                recoveryPasswordUserUseCase(email = _inputEmail.value)) {
                is Resource.Failure -> setErrorMsg(recoveryPasswordResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.recovery_password_success)
                }
            }
            loading(false)
        }


    private fun validInputs(): Boolean {
        val email = _inputEmail.value
        return (email.isNotEmpty())
    }


    fun onInputEmailChanged(inputEmail: String) {
        _inputEmail.value = inputEmail
        _enabledButton.value = inputEmail.validateEmail()
    }


    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError, successOperationMsg = null)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }

}