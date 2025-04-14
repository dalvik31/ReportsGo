package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.domain.DeleteOrderUseCase
import com.epacheco.reports.compose_reformat.domain.GetOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.UpdateStatusOrderUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val updateStatusOrderUseCase: UpdateStatusOrderUseCase,
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(OrdersUiState())
    val uiState: StateFlow<OrdersUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<OrdersUiEffect>()
    val effectFlow: SharedFlow<OrdersUiEffect> = _effectFlow


    fun handleIntent(intent: OrdersUiIntent) {
        when (intent) {
            is OrdersUiIntent.DeleteOrder -> deleteOrder(intent.orderId, intent.mainOrderId)
            OrdersUiIntent.HideDialogs -> setErrorMsg()
            is OrdersUiIntent.LoadOrders -> loadOrders(intent.mainOrderId)
            is OrdersUiIntent.UpdateStatusOrder -> updateStatusOrder(
                intent.orderId,
                intent.mainOrderId,
                intent.orderBuy
            )
        }
    }

    private fun loadOrders(mainOrderId: String) =
        viewModelScope.launch {
            loading(true)
            when (val orderResponse = getOrdersUseCase(mainOrderId)) {
                is Resource.Failure -> {
                    setErrorMsg(orderResponse.exception.message)
                    _uiState.value = _uiState.value.copy(
                        showImgEmptyList = true
                    )
                }

                is Resource.Success -> {
                    _uiState.value =

                        _uiState.value.copy(
                            orders = orderResponse.result,
                            showImgEmptyList = orderResponse.result.isEmpty()
                        )
                }
            }
            loading(false)
        }

    private fun deleteOrder(orderId: String, mainOrderId: String) =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = deleteOrderUseCase(orderId, mainOrderId)) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_delete_success)
                    loadOrders(mainOrderId)
                }
            }
            loading(false)
        }

    private fun updateStatusOrder(orderId: String, mainOrderId: String, orderBuy: Boolean) =
        viewModelScope.launch {
            loading(true)

            when (val orderMainResponse =
                updateStatusOrderUseCase(orderId, mainOrderId = mainOrderId, orderBuy)) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_update_success)
                    loadOrders(mainOrderId)
                }
            }
            loading(false)
        }


    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError, successOperationMsg = null)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }
}