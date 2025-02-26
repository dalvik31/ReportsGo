package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.FirebaseGetUserUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUserLogoutUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseUserLogoutUseCase: FirebaseUserLogoutUseCase,
    private val firebaseGetUserUseCase: FirebaseGetUserUseCase,
    ) :
    ViewModel() {

    private val _userFlow = MutableStateFlow<Resource<FirebaseUser>?>(Resource.Loading)
    val userFlow: StateFlow<Resource<FirebaseUser>?> = _userFlow

    init {
        getProfile()
    }

    private fun getProfile() = viewModelScope.launch {
        _userFlow.value = Resource.Loading
        val result = firebaseGetUserUseCase()
        _userFlow.value = result
    }

    fun logout() {
        firebaseUserLogoutUseCase()
    }

}