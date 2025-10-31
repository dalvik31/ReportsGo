package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.ClientCreateUseCase
import com.epacheco.reports.compose_reformat.domain.ClientDeleteUseCase
import com.epacheco.reports.compose_reformat.domain.ClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.ClientUpdateUseCase
import com.epacheco.reports.compose_reformat.domain.FinancesGetByClientIdUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.ClientDetailUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.DetailClientUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientInfoViewModel @Inject constructor(
    private val financesGetByClientIdUseCase: FinancesGetByClientIdUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ClientInfoUiState())
    val uiState: StateFlow<ClientInfoUiState> = _uiState


    fun handleIntent(intent: ClientInfoUiIntent) {
        when (intent) {
            ClientInfoUiIntent.HideDialogs -> setErrorMsg()
            is ClientInfoUiIntent.LoadTransactions -> getClientTransactions(intent.clientId)
        }
    }

    fun getClientTransactions(clientId: String) = viewModelScope.launch {
        loading(true)
        when (val financesResponse = financesGetByClientIdUseCase(clientId)) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        clientTransactions = financesResponse.result
                    )
                }
            }

            is Resource.Failure -> {
            }
        }
        loading(false)
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

}