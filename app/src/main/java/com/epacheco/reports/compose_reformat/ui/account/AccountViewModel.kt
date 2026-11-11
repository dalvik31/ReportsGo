package com.epacheco.reports.compose_reformat.ui.account

import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.user.SigInUserWithEmailAndPasswordUseCase
import com.epacheco.reports.compose_reformat.domain.user.SigInUserWithGoogleUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInWithGoogleUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignUpUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.supabase.AuthenticationRepository
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
    private val signInUseCase: SignInUseCase,
    private val app: ReportsApp,
    private val authenticationRepository: AuthenticationRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<AccountUiEffect>()
    val effectFlow: SharedFlow<AccountUiEffect> = _effectFlow

    private val _enabledLoginButton = MutableStateFlow(false)
    val enabledLoginButton: StateFlow<Boolean> = _enabledLoginButton

    fun handleIntent(intent: AccountUiIntent) {
        when (intent) {
            AccountUiIntent.SignUp -> onSignUp()
            AccountUiIntent.ChangePassword -> navigateToPassword()
            AccountUiIntent.SignIn -> onSignIn()
            AccountUiIntent.HideMsgError -> setErrorMsg()
            is AccountUiIntent.GoogleSignIn -> onGoogleSignIn()
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

    private fun onSignIn() {
        viewModelScope.launch {
            loading(true)
            val result = signInUseCase.execute(
                SignInUseCase.Input(
                    email =  _uiState.value.email,
                    password = _uiState.value.password
                )
            )
            when (result) {
                is SignInUseCase.Output.Success -> {
                    navigateToHome()
                }

                is SignInUseCase.Output.Failure -> {
                    setErrorMsg(result.exception.message)
                }
            }
            loading(false)
        }
    }

    fun onSignUp() {
        viewModelScope.launch {
            loading(true)
            val result = signUpUseCase.execute(
                SignUpUseCase.Input(
                    email =  _uiState.value.email,
                    password = _uiState.value.password
                )
            )
            when (result) {
                is SignUpUseCase.Output.Success -> {
                    navigateToHome()
                }
                is SignUpUseCase.Output.Failure -> {
                    setErrorMsg(result.exception.message)
                }
            }
            loading(false)
        }
    }


    fun onGoogleSignIn() {
        viewModelScope.launch {
            when (val result = authenticationRepository.signInWithGoogle()) {
                is SignInWithGoogleUseCase.Output.Success -> {}

                is SignInWithGoogleUseCase.Output.Failure -> {
                    setErrorMsg(result.exception.message)
                }
            }
            loading(false)
        }
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
    /*    loading(true)
        when (val signInResponse = signUpUseCase(_uiState.value.email, _uiState.value.password)) {
            is Resource.Failure -> setErrorMsg(
                signInResponse.exception.message?.getTranslateFireBaseErrorMsg(
                    app
                )
            )

            is Resource.Success -> navigateToHome()

        }
        loading(false)*/
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