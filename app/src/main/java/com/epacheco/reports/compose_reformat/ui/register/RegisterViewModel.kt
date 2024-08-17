package com.epacheco.reports.compose_reformat.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLoginUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLogoutUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
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
    private val firebaseUserLogoutUseCase: FirebaseUserLogoutUseCase,
    private val firebaseGetUserUseCase: FirebaseGetUserUseCase
) :
    BaseViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(Resource.Loading)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow


    init {
        getCurrentUser()
    }

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _enabledLoginButton = MutableLiveData<Boolean>()
    val enabledLoginButton: LiveData<Boolean> = _enabledLoginButton

    private val _checkRememberUser = MutableLiveData<Boolean>()
    val checkRememberUser: LiveData<Boolean> = _checkRememberUser


    fun onValueLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _enabledLoginButton.value =
            Validations.validateEmailAndPassword(email = email, password = password)
    }

    fun onValueCheckRememberUser(enable: Boolean) {
        _checkRememberUser.value = !enable
    }

    fun getCurrentUser() {
        _loginFlow.value = Resource.Loading
        val result = firebaseGetUserUseCase()
        if (result != null) _loginFlow.value = Resource.Success(result)
        else _loginFlow.value = null

    }

    fun loginWithEmail() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseUserLoginUseCase(email.value!!, password.value!!)
        _loginFlow.value = result
    }

    fun logout() {
        firebaseUserLogoutUseCase()
        _loginFlow.value = null
    }


}