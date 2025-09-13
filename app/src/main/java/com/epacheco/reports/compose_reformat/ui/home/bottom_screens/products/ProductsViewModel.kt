package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.ProductsGetByNameUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
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
class ProductsViewModel @Inject constructor(
    private val productsByNameUseCase: ProductsGetByNameUseCase
) :
    BaseViewModel() {

    private var handler: Handler? = null

    private val _inputProductName = MutableStateFlow("")
    val inputProductName: StateFlow<String> = _inputProductName

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<ProductsUiEffect>()
    val effectFlow: SharedFlow<ProductsUiEffect> = _effectFlow

    fun handleIntent(intent: ProductsUiIntent) {
        when (intent) {
            ProductsUiIntent.LoadProducts -> downloadProducts()
            ProductsUiIntent.Error -> setErrorMsg()
        }
    }

    private fun downloadProducts() {
        getHandler()?.removeCallbacksAndMessages(null)
        val productNameToSearch = _inputProductName.value.ifEmpty { null }
        if (productNameToSearch != null) {
            getHandler()?.postDelayed({
                getProductsByName(productNameToSearch)
            }, 1000)
        } else {
            getProductsByName(null)
        }


    }

    fun getProductsByName(productNameToSearch: String? = null) = viewModelScope.launch {
        loading(true)
        when (val productsResponse = productsByNameUseCase(productNameToSearch)) {
            is Resource.Failure -> {
                setErrorMsg(productsResponse.exception.message)
            }

            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        listProducts = productsResponse.result
                    )
                }
            }
        }
        loading(false)
    }

    fun onInputNameChanged(inputName: String) {
        _inputProductName.value = inputName
        downloadProducts()

    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

    fun getHandler(): Handler? {
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        return handler
    }
}