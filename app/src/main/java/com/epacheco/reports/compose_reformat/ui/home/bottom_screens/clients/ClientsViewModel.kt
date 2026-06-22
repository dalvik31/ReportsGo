package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.clients.GetClientByNameUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(private val getClientByNameUseCase: GetClientByNameUseCase) :
    BaseViewModel() {
    private var handler: Handler? = null

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
        val productNameToSearch = _uiState.value.clientName.ifEmpty { null }
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
        _uiState.update {
            it.copy(
                clientName = inputName
            )
        }
        downloadClients()
    }

    fun onPhoneClientChanged(inputPhone: String) {
        _uiState.update {
            it.copy(clientPhone = inputPhone)
        }
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