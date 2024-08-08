package com.epacheco.reports.compose_reformat.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.ui.utils.Validations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

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

}