package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.GetOrdersUseCase
import com.epacheco.reports.compose_reformat.domain.GetProductsUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.products.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsUseCase: GetProductsUseCase) :
    ViewModel() {

    private val _productsFlow = MutableStateFlow<Resource<List<Product>>?>(Resource.Loading)
    val productsFlow: StateFlow<Resource<List<Product>>?> = _productsFlow

    init {
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch {
        _productsFlow.value = Resource.Loading
        val result = productsUseCase()
        _productsFlow.value = result
    }
}