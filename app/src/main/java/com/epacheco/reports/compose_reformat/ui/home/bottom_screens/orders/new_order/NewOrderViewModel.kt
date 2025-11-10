package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.clients.GetClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.orders.CreateOrderUseCase
import com.epacheco.reports.compose_reformat.domain.orders.DeleteOrderUseCase
import com.epacheco.reports.compose_reformat.domain.orders.GetOrderByIdUseCase
import com.epacheco.reports.compose_reformat.domain.orders.UpdateOrderUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val getClientDetailUseCase: GetClientDetailUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(NewOrderUiState())
    val uiState: StateFlow<NewOrderUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<NewOrderUiEffect>()
    val effectFlow: SharedFlow<NewOrderUiEffect> = _effectFlow


    fun handleIntent(intent: NewOrderUiIntent) {
        when (intent) {
            is NewOrderUiIntent.CreateOrder -> {
                if (validInputs())
                    createOrder(intent.mainOrderId, intent.orderSeason) else
                    setErrorMsg(app.getString(R.string.order_empty_inputs_error))

            }

            is NewOrderUiIntent.DeleteOrder -> deleteOrder(intent.orderId, intent.mainOrderId)
            NewOrderUiIntent.HideDialogs -> setErrorMsg()
            NewOrderUiIntent.UpdateOrder -> if (validInputs()) updateOrder()
            else setErrorMsg(app.getString(R.string.order_empty_inputs_error))

            is NewOrderUiIntent.GetClientById -> getClientById(intent.clientId)
            is NewOrderUiIntent.GetOrderById -> getOrderById(
                intent.orderMainId,
                intent.orderId,
                intent.callClientInfo
            )

            NewOrderUiIntent.RemoveClient -> removeClient()
        }
    }

    private fun removeClient() {
        _uiState.value = _uiState.value.copy(client = null)
    }

    private fun getOrderById(orderMainId: String, orderId: String, callClientInfo: Boolean) =
        viewModelScope.launch {

            loading(true)
            when (val orderToEditResponse = getOrderByIdUseCase(orderMainId, orderId)) {
                is Resource.Failure -> {
                    setErrorMsg(orderToEditResponse.exception.message)
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            orderToEdit = orderToEditResponse.result
                        )
                    }
                    val orderClientId = orderToEditResponse.result?.orderClientId
                    if (!orderClientId.isNullOrEmpty() && callClientInfo) {
                        getClientById(orderClientId)
                    }
                    setValuesToEdit(orderToEditResponse.result)
                }
            }
            loading(false)

        }

    private fun setValuesToEdit(order: Order?) {
        order?.let {
            onInputNameChanged(it.orderName)
            onInputStatusChanged(it.orderBuy)
            onInputGenderChanged(it.orderGender)
            onInputColorChanged(it.orderColor)
            onInputSizeChanged(it.orderSize)
            onInputDescriptionChanged(it.orderDescription)
            onInputColorCodeChanged(it.orderColorCode)
            onIsNumericSizeChanged(it.orderSizeNumeric)
        }

    }


    private fun getClientById(clientId: String?) = viewModelScope.launch {
        if (!clientId.isNullOrEmpty()) {

            loading(true)
            when (val clientResponse = getClientDetailUseCase(clientId)) {
                is Resource.Failure -> {
                    setErrorMsg(clientResponse.exception.message)
                }

                is Resource.Success -> {

                    _uiState.update {
                        it.copy(
                            client = clientResponse.result
                        )
                    }
                }
            }
            loading(false)

        }
    }

    private fun createOrder(mainOrderId: String, mainOrderSeason: Season?) =
        viewModelScope.launch {
            loading(true)
            when (val orderMainMainResponse = createOrderUseCase(
                getNewOrder().apply {
                    orderListId = mainOrderId
                    orderSeason = mainOrderSeason
                }
            )) {
                is Resource.Failure -> setErrorMsg(orderMainMainResponse.exception.message)
                is Resource.Success -> {
                    if (validInputs()) {
                        _effectFlow.emit(NewOrderUiEffect.NavigateBack)
                    } else {
                    }

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
                    _effectFlow.emit(NewOrderUiEffect.NavigateBack)
                }
            }
            loading(false)
        }


    private fun updateOrder() =
        viewModelScope.launch {
            loading(true)
            val order = _uiState.value.orderToEdit

            order?.let { orderToEdit ->
                when (val updateOrderResponse =
                    updateOrderUseCase(
                        getNewOrder().copy(
                            orderId = orderToEdit.orderId,
                            orderListId = orderToEdit.orderListId,
                            orderSeason = orderToEdit.orderSeason,
                            orderClientId = _uiState.value.client?.id ?: order.orderClientId,
                            orderClientName = _uiState.value.client?.name ?: order.orderClientName
                        )
                    )) {
                    is Resource.Failure -> setErrorMsg(updateOrderResponse.exception.message)
                    is Resource.Success -> {
                        _uiState.value =
                            _uiState.value.copy(successOperationMsg = R.string.msg_order_update_success)
                        _effectFlow.emit(NewOrderUiEffect.NavigateBack)
                    }
                }
            }

            loading(false)
        }

    private fun getNewOrder(): Order =
        Order(
            orderId = DateUtils.now().toString(),
            orderName = uiState.value.productName,
            orderDescription = uiState.value.productDescription,
            orderSize = uiState.value.productSize,
            orderGender = uiState.value.productGender,
            orderColor = uiState.value.productColor,
            orderSizeNumeric = uiState.value.isProductSizeNumeric,
            orderColorCode = uiState.value.productColorCode,
            orderBuy = uiState.value.productStatus,
            orderClientId = _uiState.value.client?.id,
            orderClientName = _uiState.value.client?.name
        )


    private fun validInputs(): Boolean {
        val name = uiState.value.productName
        val desc = uiState.value.productDescription
        val size = uiState.value.productSize
        val col = uiState.value.productColor
        val gen = uiState.value.productGender
        return (name.isNotEmpty() && desc.isNotEmpty() && size.isNotEmpty() && col.isNotEmpty() && gen.isNotEmpty())
    }

    fun onInputStatusChanged(inputStatus: Boolean) {
        _uiState.update { it.copy(productStatus = inputStatus) }
    }

    fun onInputNameChanged(inputName: String) {
        _uiState.update { it.copy(productName = inputName) }
    }

    fun onInputDescriptionChanged(inputDescription: String) {
        _uiState.update { it.copy(productDescription = inputDescription) }
    }

    fun onInputSizeChanged(inputSize: String) {
        _uiState.update { it.copy(productSize = inputSize) }
    }

    fun onIsNumericSizeChanged(isNumericSize: Boolean) {
        _uiState.update { it.copy(isProductSizeNumeric = isNumericSize) }
    }

    fun onInputColorChanged(inputColor: String) {
        _uiState.update { it.copy(productColor = inputColor) }
    }

    fun onInputColorCodeChanged(inputCodeColor: String?) {
        _uiState.update { it.copy(productColorCode = inputCodeColor) }
    }

    fun onInputGenderChanged(inputGender: String) {
        _uiState.update { it.copy(productGender = inputGender) }
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successOperationMsg = null) }
    }

    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

}