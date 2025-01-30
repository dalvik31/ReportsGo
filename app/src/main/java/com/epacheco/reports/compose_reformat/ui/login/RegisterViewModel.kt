package com.epacheco.reports.compose_reformat.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLoginUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLogoutUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserSignUpUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.utils.Validations
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseUserLoginUseCase: FirebaseUserLoginUseCase,
    private val firebaseUserLogoutUseCase: FirebaseUserLogoutUseCase,
    private val firebaseGetUserUseCase: FirebaseGetUserUseCase,
    private val firebaseUserSignUpUseCase: FirebaseUserSignUpUseCase,
    private val app: ReportsApp
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(Resource.Loading)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow


    init {
        getCurrentUser()
    }

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _enabledLoginButton = MutableStateFlow(false)
    val enabledLoginButton: StateFlow<Boolean> = _enabledLoginButton


    fun onValueLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _enabledLoginButton.value =
            Validations.validateEmailAndPassword(email = email, password = password)
    }

    fun getCurrentUser() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseGetUserUseCase()
        if (result != null) _loginFlow.value = Resource.Success(result)
        else goToRegisterFlow()


    }

    fun loginWithEmail() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseUserLoginUseCase(email.value, password.value)
        _loginFlow.value = result
    }

    fun logout() {
        firebaseUserLogoutUseCase()
        goToRegisterFlow()
    }

    fun goToRegisterFlow() {
        _loginFlow.value = Resource.Failure(null)
    }

    fun createAccount() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseUserSignUpUseCase(email.value, password.value)
        _loginFlow.value = result
    }

    fun showToastMsg(msg: Int?) {
        app.showMsgToast(msg)
    }

    fun isUserLogin(): Boolean {
        return firebaseGetUserUseCase.invoke() != null
    }

}