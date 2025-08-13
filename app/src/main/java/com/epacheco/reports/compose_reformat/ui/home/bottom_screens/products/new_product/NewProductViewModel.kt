package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.ProductsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewProductViewModel @Inject constructor() :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<ProductsUiEffect>()
    val effectFlow: SharedFlow<ProductsUiEffect> = _effectFlow

    fun handleIntent(intent: ProductsUiIntent) {
        when (intent) {
            ProductsUiIntent.LoadProducts -> {}
            ProductsUiIntent.Error -> setErrorMsg()
        }
    }

    /*  private fun getProducts() = viewModelScope.launch {
          loading(true)
          when (val productsResponse = productsUseCase()) {
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

      }*/


    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }
}