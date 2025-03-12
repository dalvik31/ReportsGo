package com.epacheco.reports.compose_reformat.ui.account

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLoginUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserSignUpUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.Validations
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
    private val firebaseGetUserUseCase: FirebaseGetUserUseCase,
    private val firebaseUserSignUpUseCase: FirebaseUserSignUpUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState: StateFlow<AccountUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<AccountUiEffect>()
    val effectFlow: SharedFlow<AccountUiEffect> = _effectFlow

    private val _email = MutableStateFlow("eeph34@gmail.com")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("123456")
    val password: StateFlow<String> = _password

    private val _enabledLoginButton = MutableStateFlow(false)
    val enabledLoginButton: StateFlow<Boolean> = _enabledLoginButton

    init {
        getProfile()
    }

    fun handleIntent(intent: AccountUiIntent) {
        when (intent) {
            AccountUiIntent.Error -> setErrorMsg()
            AccountUiIntent.SignUp -> doSignUp()
            AccountUiIntent.Password -> navigateToPassword()
            AccountUiIntent.SignIn -> doSignIn()
            AccountUiIntent.GetProfile -> getProfile()
        }
    }

    private fun getProfile() = viewModelScope.launch {
        loading(true)
        when (firebaseGetUserUseCase()) {
            is Resource.Failure -> {
                /*No existe informacion del usuario nos quedamos en esta pantalla
                * para que el usuario inicie sesion o cree una cuenta*/
            }

            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(userInfoRetrieved = true)
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
            is Resource.Failure -> {
                _uiState.value =
                    _uiState.value.copy(errorMessage = signInResponse.exception.message)
            }

            is Resource.Success -> {
                navigateToHome()
            }
        }
        loading(false)
    }

    private fun doSignUp() = viewModelScope.launch {
        loading(true)
        when (val signInResponse = firebaseUserSignUpUseCase(email.value, password.value)) {
            is Resource.Failure -> {
                _uiState.value =
                    _uiState.value.copy(errorMessage = signInResponse.exception.message)
            }

            is Resource.Success -> {
                _uiState.value = _uiState.value.copy(userInfoRetrieved = true)
            }
        }
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