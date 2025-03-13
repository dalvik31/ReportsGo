package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.DeleteOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.GetOrdersUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersMainViewModel @Inject constructor(
    private val ordersUseCase: GetOrdersUseCase,
    private val deleteOrdersUseCase: DeleteOrdersUseCase
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(OrdersMainUiState())
    val uiState: StateFlow<OrdersMainUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<OrdersMainUiEffect>()
    val effectFlow: SharedFlow<OrdersMainUiEffect> = _effectFlow

    init {
        handleIntent(OrdersMainUiIntent.LoadMainOrders)
    }

    fun handleIntent(intent: OrdersMainUiIntent) {
        when (intent) {
            is OrdersMainUiIntent.DeleteMainList -> deleteMainOrder(intent.orderId)
            OrdersMainUiIntent.LoadMainOrders -> loadMainOrders()
            is OrdersMainUiIntent.UpdateMainListStatus -> TODO()
            OrdersMainUiIntent.Error -> setErrorMsg()
        }
    }

    private fun loadMainOrders() =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = ordersUseCase()) {
                is Resource.Failure -> {
                    setErrorMsg(orderMainResponse.exception.message)
                    _uiState.value = _uiState.value.copy(
                        showImgEmptyList = true
                    )
                }

                is Resource.Success -> _uiState.value =
                    _uiState.value.copy(
                        orders = orderMainResponse.result,
                        showImgEmptyList = orderMainResponse.result.isEmpty()
                    )
            }
            loading(false)
        }

    private fun deleteMainOrder(orderId: String) =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = deleteOrdersUseCase(orderId)) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> loadMainOrders()
            }
            loading(false)
        }

    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }
}