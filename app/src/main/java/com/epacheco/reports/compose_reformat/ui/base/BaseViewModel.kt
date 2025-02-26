package com.epacheco.reports.compose_reformat.ui.base

import androidx.lifecycle.ViewModel
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {
    private val _msgErrorFlow = MutableStateFlow<String?>(null)
    val msgErrorFlow: StateFlow<String?> = _msgErrorFlow


    fun setMsgError(error: String?) {
        _msgErrorFlow.value = error
    }
}