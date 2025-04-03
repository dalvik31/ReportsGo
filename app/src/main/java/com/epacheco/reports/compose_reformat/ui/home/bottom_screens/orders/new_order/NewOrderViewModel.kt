package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.CreateOrderUseCase
import com.epacheco.reports.compose_reformat.domain.DeleteOrderUseCase
import com.epacheco.reports.compose_reformat.domain.UpdateOrderUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season
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
class NewOrderViewModel @Inject constructor(
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _inputProductStatus = MutableStateFlow(false)
    val inputProductStatus: StateFlow<Boolean> = _inputProductStatus

    private val _inputProductName = MutableStateFlow("")
    val inputProductName: StateFlow<String> = _inputProductName

    private val _inputProductDescription = MutableStateFlow("")
    val inputProductDescription: StateFlow<String> = _inputProductDescription

    private val _inputProductSize = MutableStateFlow("")
    val inputProductSize: StateFlow<String> = _inputProductSize

    private val _isProductSizeNumeric = MutableStateFlow(false)
    val isProductSizeNumeric: StateFlow<Boolean> = _isProductSizeNumeric

    private val _inputProductColor = MutableStateFlow("")
    val inputProductColor: StateFlow<String> = _inputProductColor

    private val _inputProductColorCode = MutableStateFlow<String?>(null)
    val inputProductColorCode: StateFlow<String?> = _inputProductColorCode

    private val _inputProductGender = MutableStateFlow("")
    val inputProductGender: StateFlow<String> = _inputProductGender

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
            is NewOrderUiIntent.UpdateOrder -> if (validInputs()) updateOrder(intent.order)
            else setErrorMsg(app.getString(R.string.order_empty_inputs_error))

            is NewOrderUiIntent.UpdateStatusOrder -> TODO()
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


    private fun updateOrder(order: Order) =
        viewModelScope.launch {
            loading(true)
            when (val updateOrderResponse =
                updateOrderUseCase(
                    getNewOrder().copy(
                        orderId = order.orderId,
                        orderListId = order.orderListId,
                        orderSeason = order.orderSeason
                    )
                )) {
                is Resource.Failure -> setErrorMsg(updateOrderResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_update_success)
                    _effectFlow.emit(NewOrderUiEffect.NavigateBack)
                }
            }
            loading(false)
        }

    private fun getNewOrder(): Order =
        Order(
            orderId = DateUtils.now().toString(),
            nameOrder = _inputProductName.value,
            orderDescription = _inputProductDescription.value,
            orderSize = _inputProductSize.value,
            orderGender = _inputProductGender.value,
            orderColor = _inputProductColor.value,
            orderSizeNumeric = _isProductSizeNumeric.value,
            orderColorCode = _inputProductColorCode.value,
            orderStatus = if (_inputProductStatus.value) OrderStatus.DONE else OrderStatus.IN_PROGRESS
        )


    private fun validInputs(): Boolean {
        val name = _inputProductName.value
        val desc = _inputProductDescription.value
        val size = _inputProductSize.value
        val col = _inputProductColor.value
        val gen = _inputProductGender.value
        return (name.isNotEmpty() && desc.isNotEmpty() && size.isNotEmpty() && col.isNotEmpty() && gen.isNotEmpty())
    }

    fun onInputStatusChanged(inputStatus: Boolean) {
        _inputProductStatus.value = inputStatus
    }

    fun onInputNameChanged(inputName: String) {
        _inputProductName.value = inputName
    }

    fun onInputDescriptionChanged(inputDescription: String) {
        _inputProductDescription.value = inputDescription
    }

    fun onInputSizeChanged(inputSize: String) {
        _inputProductSize.value = inputSize
    }

    fun onIsNumericSizeChanged(isNumericSize: Boolean) {
        _isProductSizeNumeric.value = isNumericSize
    }

    fun onInputColorChanged(inputColor: String) {
        _inputProductColor.value = inputColor
    }

    fun onInputColorCodeChanged(inputCodeColor: String?) {
        _inputProductColorCode.value = inputCodeColor
    }


    fun onInputGenderChanged(inputGender: String) {
        _inputProductGender.value = inputGender
    }


    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError, successOperationMsg = null)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }

}