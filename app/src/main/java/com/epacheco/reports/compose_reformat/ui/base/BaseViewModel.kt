package com.epacheco.reports.compose_reformat.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    private val _msgErrorFlow = MutableStateFlow<String?>(null)
    val msgErrorFlow: StateFlow<String?> = _msgErrorFlow

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun setMsgError(error: String?) {
        _msgErrorFlow.value = error
    }

    fun setLoading(load: Boolean) {
        _loading.value = load
    }

    abstract fun setErrorMsg(msgError: String? = null)
    abstract fun loading(showLoading: Boolean = false)

}