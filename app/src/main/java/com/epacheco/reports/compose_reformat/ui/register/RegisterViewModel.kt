package com.epacheco.reports.compose_reformat.ui.register

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.auth.FirebaseAuthRepository
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.Validations
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseAuthRepository: AuthRepository) :
    BaseViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    val currentUser: FirebaseUser?
        get() = firebaseAuthRepository.currentUser


    init {
        _loginFlow.value = Resource.Loading
        if (firebaseAuthRepository.currentUser != null) {
            _loginFlow.value = Resource.Success(firebaseAuthRepository.currentUser!!)
        }else{
            _loginFlow.value = null
        }
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

    fun loginWithEmail() = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseAuthRepository.login(email.value!!, password.value!!)
        _loginFlow.value = result
    }

    fun logout() {
        firebaseAuthRepository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }


}