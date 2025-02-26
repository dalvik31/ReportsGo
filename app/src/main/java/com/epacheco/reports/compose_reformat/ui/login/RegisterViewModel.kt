package com.epacheco.reports.compose_reformat.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLoginUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserSignUpUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.Validations
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseUserLoginUseCase: FirebaseUserLoginUseCase,
    private val firebaseGetUserUseCase: FirebaseGetUserUseCase,
    private val firebaseUserSignUpUseCase: FirebaseUserSignUpUseCase,
    private val app: ReportsApp
) : BaseViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(Resource.Loading)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _enabledLoginButton = MutableStateFlow(false)
    val enabledLoginButton: StateFlow<Boolean> = _enabledLoginButton

    init {
        getSessionUser()
    }


    private fun getSessionUser() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseGetUserUseCase()
        _loginFlow.value = result

    }

    fun onValueLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _enabledLoginButton.value =
            Validations.validateEmailAndPassword(email = email, password = password)
    }

    fun loginWithEmail() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseUserLoginUseCase(email.value, password.value)
        _loginFlow.value = result
    }

    fun goToRegisterFlow() {
        _loginFlow.value = null
    }

    fun createAccount() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseUserSignUpUseCase(email.value, password.value)
        _loginFlow.value = result
    }
}