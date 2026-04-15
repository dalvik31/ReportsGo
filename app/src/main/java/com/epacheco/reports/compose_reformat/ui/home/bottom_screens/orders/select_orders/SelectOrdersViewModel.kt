package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.select_orders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.orders.CreateMainOrderUseCase
import com.epacheco.reports.compose_reformat.domain.orders.GetOrdersMainListUseCase
import com.epacheco.reports.compose_reformat.domain.orders.MoveOrdersUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.SeasonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectOrdersViewModel @Inject constructor(
    private val getOrdersMainListUseCase: GetOrdersMainListUseCase,
    private val createOrderMainUseCase: CreateMainOrderUseCase,
    private val moveOrdersUseCase: MoveOrdersUseCase
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(SelectOrdersUiState())
    val uiState: StateFlow<SelectOrdersUiState> = _uiState


    fun handleIntent(intent: SelectOrdersUiIntent) {
        when (intent) {
            SelectOrdersUiIntent.HideDialogs -> setErrorMsg()
            SelectOrdersUiIntent.CreateOrderMain -> createOrderMain()
            SelectOrdersUiIntent.LoadSelectOrders -> getClientOrders()
            is SelectOrdersUiIntent.SetOrderMainId -> setOrderMainId(intent.orderMainId)
        }
    }


    private fun setOrderMainId(orderMainId: String?) {
        orderMainId?.let {
            _uiState.update {
                it.copy(orderMainId = orderMainId)
            }
        }
    }

    private fun getClientOrders() = viewModelScope.launch {
        loading(true)
        when (val ordersResponse = getOrdersMainListUseCase()) {
            is Resource.Success -> {
                val listOrders =
                    ordersResponse.result.filter { it.orderId != uiState.value.orderMainId }
                _uiState.update {
                    it.copy(
                        orderMainList = listOrders
                    )
                }
            }

            is Resource.Failure -> {}
        }
        loading(false)
    }

    private fun createOrderMain() = viewModelScope.launch {
        loading(true)
        val orderId = System.currentTimeMillis()
        when (val createOrderMainResponse = createOrderMainUseCase(
            OrderMain(
                orderId = orderId.toString(),
                dateOrder = orderId.toString(),
                nameOrder = "",
                orderDate = DateUtils.dateFormat(orderId.toString(), DateUtils.FORMAT_DATE2),
                orderSeason = SeasonUtils.getSeason()

            )
        )) {
            is Resource.Success -> {
                getClientOrders()
            }

            is Resource.Failure -> {
                setErrorMsg(createOrderMainResponse.exception.message)
            }
        }
        loading(false)
    }


    fun moveOrders(orders: List<Order>, mainOrderId: String) = viewModelScope.launch {
        loading(true)
        when (val moveOrdersResponse = moveOrdersUseCase(orders, mainOrderId)) {
            is Resource.Success -> {
                _uiState.update { it.copy(successMessage = "Se movieron los pedidos") }
            }

            is Resource.Failure -> {
                setErrorMsg(moveOrdersResponse.exception.message)
            }
        }
        loading(false)

    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successMessage = null) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

}