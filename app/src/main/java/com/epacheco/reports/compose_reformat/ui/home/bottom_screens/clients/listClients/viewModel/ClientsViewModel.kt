package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.viewModel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.clients.GetClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.clients.GetClientByNameUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view.ClientUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.view.ClientsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import kotlin.text.ifEmpty

@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val clientsUseCase: GetClientByNameUseCase,
    private val getClientDetailUseCase: GetClientDetailUseCase,
    private val getClientByNameUseCase: GetClientByNameUseCase
) :
    BaseViewModel() {
    private var handler: Handler? = null

    private val _inputClientName = MutableStateFlow("")
    val inputClientName: StateFlow<String> = _inputClientName

    private val _uiState = MutableStateFlow(ClientsUiState())
    val uiState: StateFlow<ClientsUiState> = _uiState



    fun handleIntent(intent: ClientUiIntent) {
        when (intent) {
            is ClientUiIntent.Error -> setErrorMsg(intent.msgError)
            is ClientUiIntent.LoadClients -> downloadClients()
        }
    }


    private fun downloadClients() {
        getHandler()?.removeCallbacksAndMessages(null)
        val productNameToSearch = _inputClientName.value.ifEmpty { null }
        if (productNameToSearch != null) {
            getHandler()?.postDelayed({
                getClientsByName(productNameToSearch)
            }, 1000)
        } else {
            getClientsByName(null)
        }
    }

    fun getClientsByName(clientNameToSearch: String? = null) = viewModelScope.launch {
        loading(true)
        when (val clientsResponse = getClientByNameUseCase(clientNameToSearch)) {
            is Resource.Failure -> {
                setErrorMsg(clientsResponse.exception.message)
            }

            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        listClients = clientsResponse.result
                    )
                }
            }
        }
        loading(false)
    }

    fun onInputNameChanged(inputName: String) {
        _inputClientName.value = inputName
        downloadClients()
    }

    fun getHandler(): Handler? {
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        return handler
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError) }
    }

    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }
}