package com.epacheco.reports.compose_reformat.ui.account

import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.user.SigInUserWithEmailAndPasswordUseCase
import com.epacheco.reports.compose_reformat.domain.user.SigInUserWithGoogleUseCase
import com.epacheco.reports.compose_reformat.domain.user.SignUpUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.Validations
import com.epacheco.reports.compose_reformat.utils.extensions.getTranslateFireBaseErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val sigInUserWithEmailAndPasswordUseCase: SigInUserWithEmailAndPasswordUseCase,
    private val sigInUserWithGoogleUseCase: SigInUserWithGoogleUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val app: ReportsApp
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<AccountUiEffect>()
    val effectFlow: SharedFlow<AccountUiEffect> = _effectFlow

    private val _enabledLoginButton = MutableStateFlow(false)
    val enabledLoginButton: StateFlow<Boolean> = _enabledLoginButton

    fun handleIntent(intent: AccountUiIntent) {
        when (intent) {
            AccountUiIntent.SignUp -> doSignUp()
            AccountUiIntent.ChangePassword -> navigateToPassword()
            AccountUiIntent.SignIn -> doSignIn()
            AccountUiIntent.HideMsgError -> setErrorMsg()
            is AccountUiIntent.GoogleSignIn -> googleSignIn(intent.getCredentialResponse)
        }
    }

    private fun googleSignIn(credentialResponse: GetCredentialResponse) {
        loading(true)
        viewModelScope.launch {
            try {
                when (val signInResponse = sigInUserWithGoogleUseCase(credentialResponse)) {
                    is Resource.Failure -> setErrorMsg(
                        signInResponse.exception.message?.getTranslateFireBaseErrorMsg(
                            app
                        )
                    )

                    is Resource.Success -> navigateToHome()
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        loading(false)
    }

    fun onValueLoginChanged(email: String, password: String) {
        _uiState.value = _uiState.value.copy(email = email, password = password)
        _enabledLoginButton.value =
            Validations.validateEmailAndPassword(email = email, password = password)
    }

    private fun doSignIn() = viewModelScope.launch {
        loading(true)
        when (val signInResponse =
            sigInUserWithEmailAndPasswordUseCase(_uiState.value.email, _uiState.value.password)) {
            is Resource.Failure -> setErrorMsg(
                signInResponse.exception.message?.getTranslateFireBaseErrorMsg(
                    app
                )
            )

            is Resource.Success -> navigateToHome()
        }
        loading(false)
    }

    private fun doSignUp() = viewModelScope.launch {
        loading(true)
        when (val signInResponse = signUpUseCase(_uiState.value.email, _uiState.value.password)) {
            is Resource.Failure -> setErrorMsg(
                signInResponse.exception.message?.getTranslateFireBaseErrorMsg(
                    app
                )
            )

            is Resource.Success -> navigateToHome()

        }
        loading(false)
    }

    private fun navigateToPassword() {
        viewModelScope.launch {
            _effectFlow.emit(AccountUiEffect.NavigateToPassword)
        }
    }

    private fun navigateToHome() {
        viewModelScope.launch {
            _effectFlow.emit(AccountUiEffect.NavigateToHome)
        }
    }


    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }
}