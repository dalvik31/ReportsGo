package com.epacheco.reports.compose_reformat.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {
    abstract fun setErrorMsg(msgError: String? = null)
    abstract fun loading(showLoading: Boolean = false)
}