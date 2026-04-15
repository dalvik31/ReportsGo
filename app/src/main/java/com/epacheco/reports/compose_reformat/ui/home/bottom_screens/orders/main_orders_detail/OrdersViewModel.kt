package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.domain.orders.DeleteOrderUseCase
import com.epacheco.reports.compose_reformat.domain.orders.UpdateStatusOrderMainUseCase
import com.epacheco.reports.compose_reformat.domain.orders.GetOrdersListUseCase
import com.epacheco.reports.compose_reformat.domain.orders.UpdateOrderStatusUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersListUseCase: GetOrdersListUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase,
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
                intent.orderBuy,
                intent.locationLat,
                intent.locationLong,
                address = intent.address
            )

            is OrdersUiIntent.SetOrderSelected -> setSelectedOrder(intent.orderSelected)
        }
    }

    private fun loadOrders(mainOrderId: String) =
        viewModelScope.launch {
            loading(true)
            when (val orderResponse = getOrdersListUseCase(mainOrderId)) {
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
                            showImgEmptyList = orderResponse.result.isEmpty(),
                            progressOrders = geProgressList(orderResponse.result)
                        )

                }
            }
            loading(false)
        }


    private fun geProgressList(orderLists: List<Order>): Float {
        var countOrders = 0f
        if (orderLists.isNotEmpty()) {

            orderLists.forEach {
                if (it.orderBuy) {
                    countOrders++
                }
            }
            countOrders /= orderLists.size
        }
        return countOrders
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

    private fun updateStatusOrder(
        orderId: String,
        mainOrderId: String,
        orderBuy: Boolean,
        latitude: Double? = null,
        longitude: Double? = null,
        address: String? = null
    ) =
        viewModelScope.launch {
            loading(true)

            when (val orderMainResponse =
                updateOrderStatusUseCase(
                    orderId,
                    mainOrderId = mainOrderId,
                    orderBuy,
                    latitude,
                    longitude,
                    address = address
                )) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_update_success)
                    loadOrders(mainOrderId)
                }
            }
            loading(false)
        }


    private fun setSelectedOrder(orderSelected: Order?) {
        _uiState.update { it.copy(orderSelected = orderSelected) }
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successOperationMsg = null) }
    }

    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }
}