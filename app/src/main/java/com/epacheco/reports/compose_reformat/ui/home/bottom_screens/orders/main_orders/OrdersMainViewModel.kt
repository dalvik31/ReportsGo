package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.OrderMainCreateUseCase
import com.epacheco.reports.compose_reformat.domain.OrderMainDeleteUseCase
import com.epacheco.reports.compose_reformat.domain.OrderMainListUseCase
import com.epacheco.reports.compose_reformat.domain.OrderMainUpdateStatusUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.SeasonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersMainViewModel @Inject constructor(
    private val orderMainListUseCase: OrderMainListUseCase,
    private val orderMainDeleteUseCase: OrderMainDeleteUseCase,
    private val orderMainCreateUseCase: OrderMainCreateUseCase,
    private val orderMainUpdateStatusUseCase: OrderMainUpdateStatusUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _inputList = MutableStateFlow("")
    val inputList: StateFlow<String> = _inputList

    private val _uiState = MutableStateFlow(OrdersMainUiState())
    val uiState: StateFlow<OrdersMainUiState> = _uiState


    private val _effectFlow = MutableSharedFlow<OrdersMainUiEffect>()
    val effectFlow: SharedFlow<OrdersMainUiEffect> = _effectFlow

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

            is OrdersMainUiIntent.GoToListOrders -> navigateToElementsOrderList(
                intent.orderMainId,
                intent.orderSeason,
                intent.orderNameMain
            )
        }
    }

    private fun checkUnCompleteOrders() {
        val listInProgress = _uiState.value.orderMains
        if (listInProgress.isNotEmpty()) {
            val listOrderComplete = arrayListOf<String>()
            val listOrderInProgress = arrayListOf<String>()
            for (itemProgressList in listInProgress) {
                val listMainOrder = itemProgressList.orderLists
                if (!listMainOrder.isNullOrEmpty()) {

                    if (itemProgressList.geProgressList() == 1f && itemProgressList.orderStatus == OrderStatus.IN_PROGRESS) {
                        if (!listOrderComplete.contains(itemProgressList.orderId)) {
                            listOrderComplete.add(itemProgressList.orderId)
                        }
                    } else if (itemProgressList.geProgressList() < 1f && itemProgressList.orderStatus == OrderStatus.DONE) {
                        if (!listOrderInProgress.contains(itemProgressList.orderId)) {
                            listOrderInProgress.add(itemProgressList.orderId)
                        }
                    }
                }

            }

            for (orderMainIdItem in listOrderComplete) {
                updateStatusOrder(orderMainIdItem, OrderStatus.IN_PROGRESS, true)
            }
            for (orderMainIdItem in listOrderInProgress) {
                updateStatusOrder(orderMainIdItem, OrderStatus.DONE, true)
            }
        }
    }


    private fun loadMainOrders() =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = orderMainListUseCase()) {
                is Resource.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        showImgEmptyList = true
                    )
                }

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(
                            orderMains = orderMainResponse.result,
                            showImgEmptyList = orderMainResponse.result.isEmpty(),
                        )
                    checkUnCompleteOrders()
                }
            }
            loading(false)
        }

    private fun reloadMainOrders() =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = orderMainListUseCase()) {
                is Resource.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        showImgEmptyList = true
                    )
                }

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(
                            orderMains = orderMainResponse.result,
                            showImgEmptyList = orderMainResponse.result.isEmpty(),
                        )
                }
            }
            loading(false)
        }

    private fun deleteMainOrder(orderId: String) =
        viewModelScope.launch {
            loading(true)
            when (val orderMainResponse = orderMainDeleteUseCase(orderId)) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_list_delete_success)
                    loadMainOrders()
                }
            }
            loading(false)
        }

    private fun updateStatusOrder(
        orderId: String,
        orderStatus: OrderStatus,
        comeCheckOrder: Boolean = false
    ) =
        viewModelScope.launch {
            loading(true)
            val newOrderStatus =
                if (orderStatus == OrderStatus.DONE) OrderStatus.IN_PROGRESS else OrderStatus.DONE
            when (val orderMainResponse = orderMainUpdateStatusUseCase(orderId, newOrderStatus)) {
                is Resource.Failure -> setErrorMsg(orderMainResponse.exception.message)
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successOperationMsg = R.string.msg_order_list_update_success)
                    if (comeCheckOrder) {
                        reloadMainOrders()
                    } else {
                        loadMainOrders()
                    }


                }
            }
            loading(false)
        }


    fun onValueInputListChanged(input: String) {
        _inputList.value = input
    }

    private fun navigateToElementsOrderList(
        orderMainId: String,
        season: Season?,
        nameOrderMain: String
    ) {
        viewModelScope.launch {
            _effectFlow.emit(OrdersMainUiEffect.NavigateToElementsMain(orderMainId, season, nameOrderMain))
        }
    }

    private fun createMainList() =
        viewModelScope.launch {
            loading(true)
            val orderId = System.currentTimeMillis()
            when (val orderMainMainResponse = orderMainCreateUseCase(
                OrderMain(
                    orderId = orderId.toString(),
                    dateOrder = orderId.toString(),
                    nameOrder = getNameMainOrder(),
                    orderDate = DateUtils.format(orderId, DateUtils.FORMAT_DATE2),
                    orderSeason = SeasonUtils.getSeason()

                )
            )) {
                is Resource.Failure -> setErrorMsg(orderMainMainResponse.exception.message)
                is Resource.Success -> {
                    _inputList.value = ""
                    loadMainOrders()
                }
            }
            loading(false)
        }

    private fun getNameMainOrder(): String {
        return _inputList.value

    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError, successOperationMsg = null)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }
}