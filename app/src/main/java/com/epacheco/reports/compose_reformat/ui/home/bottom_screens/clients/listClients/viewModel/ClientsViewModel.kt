package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.listClients.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.ClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.ClientListUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
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

@HiltViewModel
class ClientsViewModel @Inject constructor(private val clientsUseCase: ClientListUseCase, private val clientDetailUseCase: ClientDetailUseCase) :
    BaseViewModel() {

    private val _clientsFlow = MutableStateFlow(ClientsUiState())
    val clientsFlow: StateFlow<ClientsUiState> = _clientsFlow

    fun getClientDetail(clientId: String) = viewModelScope.launch {
        when (val clientsResponse = clientDetailUseCase(clientId)) {
            is Resource.Success -> {
//                val client = clientsResponse.result
//                val date = Date(client.datePayment.toLong()) // ← Así de simple
//                val fecha = dateFormat(date)
                Log.e("aqui", "ClientsViewModel SUCCESSS: ${clientsResponse.result}")
            }

            is Resource.Failure -> {
                Log.e("aqui", "ERRORRRR: message: ${clientsResponse.exception} ")
            }
        }
    }
    fun dateFormat(timestamp: Date): String {
        val sdf = SimpleDateFormat("dd / MMMM / yyyy", Locale("es"))
        sdf.setTimeZone(TimeZone.getDefault()) // Opcional: ajusta la zona horaria
        return sdf.format(timestamp).uppercase() // ← ¡Aquí la magia!
    }

    fun getClients() = viewModelScope.launch {
        loading(true)
        when (val clientsResponse = clientsUseCase()) {
            is Resource.Success -> {
                _clientsFlow.update {
                    it.copy(
                        listClients = clientsResponse.result
                    )
                }
            }

            is Resource.Failure -> {
                _clientsFlow.update {
                    it.copy(
                        errorMessage = clientsResponse.exception.message
                    )
                }
            }
        }
        loading(false)
    }

    override fun setErrorMsg(msgError: String?) {
        _clientsFlow.update { it.copy(errorMessage = msgError) }
    }

    override fun loading(showLoading: Boolean) {
        _clientsFlow.update { it.copy(isLoading = showLoading) }
    }
}