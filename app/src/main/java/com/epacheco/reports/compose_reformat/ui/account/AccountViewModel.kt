package com.epacheco.reports.compose_reformat.ui.account

import android.util.Log
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.FirebaseUserGoogleLoginUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLoginUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserSignUpUseCase
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
    private val firebaseUserLoginUseCase: FirebaseUserLoginUseCase,
    private val firebaseUserGoogleLoginUseCase: FirebaseUserGoogleLoginUseCase,
    private val firebaseUserSignUpUseCase: FirebaseUserSignUpUseCase,
    private val app: ReportsApp
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<AccountUiEffect>()
    val effectFlow: SharedFlow<AccountUiEffect> = _effectFlow

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _enabledLoginButton = MutableStateFlow(false)
    val enabledLoginButton: StateFlow<Boolean> = _enabledLoginButton

    init {
        // getProfile()
    }

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
        //val credentialManager = CredentialManager.create(app)
        viewModelScope.launch {
            try {
                /*val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = app,
                )*/
                when (val signInResponse = firebaseUserGoogleLoginUseCase(credentialResponse)) {
                    is Resource.Failure -> setErrorMsg(
                        signInResponse.exception.message?.getTranslateFireBaseErrorMsg(
                            app
                        )
                    )

                    is Resource.Success -> navigateToHome()
                }


            } catch (e: Throwable) {
                Log.e("TAG", "handleSignIn google ${e.message}")
            }
        }
        loading(false)
    }
    fun onValueLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _enabledLoginButton.value =
            Validations.validateEmailAndPassword(email = email, password = password)
    }

    private fun doSignIn() = viewModelScope.launch {
        loading(true)
        when (val signInResponse = firebaseUserLoginUseCase(email.value, password.value)) {
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
        when (val signInResponse = firebaseUserSignUpUseCase(email.value, password.value)) {
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