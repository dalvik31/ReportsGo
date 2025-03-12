package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.GetOrdersUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val ordersUseCase: GetOrdersUseCase) :
    BaseViewModel() {

    private val _ordersFlow = MutableStateFlow<Resource<List<Order>>?>(null)
    val ordersFlow: StateFlow<Resource<List<Order>>?> = _ordersFlow

    init {
        //getOrders()
    }

    private fun getOrders() = viewModelScope.launch {
        //_ordersFlow.value = Resource.Waiting
        val result = ordersUseCase()
        _ordersFlow.value = result
    }

    override fun setErrorMsg(msgError: String?) {
        TODO("Not yet implemented")
    }

    override fun loading(showLoading: Boolean) {
        TODO("Not yet implemented")
    }
}