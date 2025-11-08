package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.orders

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.orders.GetOrdersMainListUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientOrdersViewModel @Inject constructor(
    private val getOrdersMainListUseCase: GetOrdersMainListUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ClientOrdersUiState())
    val uiState: StateFlow<ClientOrdersUiState> = _uiState


    fun handleIntent(intent: ClientOrdersUiIntent) {
        when (intent) {
            ClientOrdersUiIntent.HideDialogs ->  setErrorMsg()
            ClientOrdersUiIntent.LoadClientOrders -> getClientOrders()
        }
    }

    fun getClientOrders() = viewModelScope.launch {
        loading(true)
        when (val ordersResponse = getOrdersMainListUseCase()) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        orderMainList = ordersResponse.result
                    )
                }
            }
            is Resource.Failure -> {}
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