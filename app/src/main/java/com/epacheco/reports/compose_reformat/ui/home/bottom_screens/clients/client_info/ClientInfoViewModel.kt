package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_info

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.finances.GetFinancesByClientIdUseCase
import com.epacheco.reports.compose_reformat.domain.orders.GetOrdersMainListUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientInfoViewModel @Inject constructor(
    private val getFinancesByClientIdUseCase: GetFinancesByClientIdUseCase,
    private val getOrdersMainListUseCase: GetOrdersMainListUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ClientInfoUiState())
    val uiState: StateFlow<ClientInfoUiState> = _uiState


    fun handleIntent(intent: ClientInfoUiIntent) {
        when (intent) {
            ClientInfoUiIntent.HideDialogs -> setErrorMsg()
            is ClientInfoUiIntent.LoadTransactions -> getClientTransactions(intent.clientId)
            is ClientInfoUiIntent.LoadOrders -> getClientOrders(intent.clientId)
        }
    }

    fun getClientTransactions(clientId: String) = viewModelScope.launch {
        loading(true)
        when (val financesResponse = getFinancesByClientIdUseCase(clientId)) {
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

    fun getClientOrders(clientId: String) = viewModelScope.launch {
        loading(true)
        when (val getClientOrderResponse = getOrdersMainListUseCase()) {
            is Resource.Success -> {
                val listOrder = arrayListOf<Order>()
                getClientOrderResponse.result.forEach { orderMainList ->
                    orderMainList.orderLists?.let {

                        it.forEach { order ->
                            if (order.value?.orderClientId == clientId) {
                                order.let {
                                    listOrder.add(order.value!!)
                                }
                            }
                        }

                    }

                }
                _uiState.update {
                    it.copy(
                        clientOrders = listOrder
                    )
                }

            }

            is Resource.Failure -> {
                setErrorMsg(getClientOrderResponse.exception.message)
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