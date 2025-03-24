package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders_main

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.CreateOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.DeleteOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.GetOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.UpdateStatusOrderUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersMainViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val deleteOrdersUseCase: DeleteOrdersUseCase,
    private val createOrdersUseCase: CreateOrdersUseCase,
    private val updateStatusOrderUseCase: UpdateStatusOrderUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _inputList = MutableStateFlow("")
    val inputList: StateFlow<String> = _inputList

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
            OrdersMainUiIntent.HideDialogs -> setErrorMsg()
            OrdersMainUiIntent.CreateOrderMainList -> createMainList()
            is OrdersMainUiIntent.UpdateStatusMainList -> updateStatusOrder(
                intent.orderId,
                intent.orderStatus
            )

            is OrdersMainUiIntent.GoToListOrders -> navigateToElementsOrderList(intent.orderMainId)
        }
    }

    private fun loadMainOrders() =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = getOrdersUseCase()) {
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
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_list_delete_success)
                    loadMainOrders()
                }
            }
            loading(false)
        }

    private fun updateStatusOrder(orderId: String, orderStatus: OrderStatus) =
        viewModelScope.launch {
            loading(true)
            val newOrderStatus =
                if (orderStatus == OrderStatus.DONE) OrderStatus.IN_PROGRESS else OrderStatus.DONE
            when (val orderMainResponse = updateStatusOrderUseCase(orderId, newOrderStatus)) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_list_update_success)
                    loadMainOrders()
                }
            }
            loading(false)
        }


    fun onValueInputListChanged(input: String) {
        _inputList.value = input
    }

    private fun navigateToElementsOrderList(orderMainId: String) {
        viewModelScope.launch {
            _effectFlow.emit(OrdersMainUiEffect.NavigateToElementsMain(orderMainId))
        }
    }

    private fun createMainList() =
        viewModelScope.launch {
            loading(true)
            val orderId = System.currentTimeMillis()
            when (val orderMainResponse = createOrdersUseCase(
                Order(
                    orderId = orderId.toString(),
                    nameOrder = getNameList(),
                    orderDate = DateUtils.format(orderId, DateUtils.FORMAT_DATE2),

                    )
            )) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _inputList.value = ""
                    loadMainOrders()
                }
            }
            loading(false)
        }

    private fun getNameList(): String = _inputList.value.ifEmpty {
        app.getString(
            R.string.title_list_default_name,
            DateUtils.format(System.currentTimeMillis(), DateUtils.FORMAT_DATE2)
        )
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError, successOperationMsg = null)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }
}