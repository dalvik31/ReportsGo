package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.CreateMainOrderUseCase
import com.epacheco.reports.compose_reformat.domain.CreateOrderUseCase
import com.epacheco.reports.compose_reformat.domain.DeleteMainOrderUseCase
import com.epacheco.reports.compose_reformat.domain.GetMainOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.GetOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.UpdateStatusOrderUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
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
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val deleteMainOrderUseCase: DeleteMainOrderUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val updateStatusOrderUseCase: UpdateStatusOrderUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _inputList = MutableStateFlow("")
    val inputList: StateFlow<String> = _inputList

    private val _uiState = MutableStateFlow(OrdersUiState())
    val uiState: StateFlow<OrdersUiState> = _uiState


    private val _effectFlow = MutableSharedFlow<OrdersUiEffect>()
    val effectFlow: SharedFlow<OrdersUiEffect> = _effectFlow


    fun handleIntent(intent: OrdersUiIntent) {
        when (intent) {
            is OrdersUiIntent.CreateOrder -> createOrder(intent.mainOrderId)
            is OrdersUiIntent.DeleteOrder -> TODO()
            is OrdersUiIntent.GoToCreateOrder -> TODO()
            OrdersUiIntent.HideDialogs -> setErrorMsg()
            is OrdersUiIntent.LoadOrders -> loadOrders(intent.mainOrderId)
            is OrdersUiIntent.UpdateStatusOrder -> TODO()
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

                is Resource.Success -> _uiState.value =
                    _uiState.value.copy(
                        orders = orderResponse.result,
                        showImgEmptyList = orderResponse.result.isEmpty()
                    )
            }
            loading(false)
        }

    /*private fun deleteMainOrder(orderId: String) =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = deleteMainOrderUseCase(orderId)) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_list_delete_success)
                    loadMainOrders()
                }
            }
            loading(false)
        }*/

    /* private fun updateStatusOrder(orderId: String, orderStatus: OrderStatus) =
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
         }*/


    fun onValueInputListChanged(input: String) {
        _inputList.value = input
    }

    /*private fun navigateToElementsOrderList(orderMainId: String) {
        viewModelScope.launch {
            _effectFlow.emit(OrdersMainUiEffect.NavigateToElementsMain(orderMainId))
        }
    }*/

    private fun createOrder(mainOrderId: String) =
        viewModelScope.launch {
            loading(true)
            val orderId = System.currentTimeMillis()
            when (val orderMainMainResponse = createOrderUseCase(
                Order(
                    orderListId = mainOrderId,
                    orderId = orderId.toString(),
                    nameOrder = getNameList(),
                )
            )) {
                is Resource.Failure -> setErrorMsg(orderMainMainResponse.exception.message)
                is Resource.Success -> {
                    _inputList.value = ""
                    loadOrders(mainOrderId)
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