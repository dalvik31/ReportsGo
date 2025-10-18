package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.ClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.ClientListUseCase
import com.epacheco.reports.compose_reformat.domain.FinancesUseCase
import com.epacheco.reports.compose_reformat.domain.ProductsGetByIdUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val clientDetailUseCase: ClientDetailUseCase,
    private val productsGetByIdUseCase: ProductsGetByIdUseCase
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(SalesUiState())
    val uiState: StateFlow<SalesUiState> = _uiState


    fun handleIntent(intent: SalesUiIntent) {
        when (intent) {
            SalesUiIntent.Error -> setErrorMsg()
            is SalesUiIntent.GetClientById -> getClientById(intent.clientId)
            is SalesUiIntent.GetProductById -> getProductById(intent.productId)
            is SalesUiIntent.UpdateStock -> updateStock(intent.product, intent.incrementValue)
            is SalesUiIntent.RemoveProductList -> removeProduct(intent.productId)
        }
    }

    private fun getClientById(clientId: String?) = viewModelScope.launch {
        if (!clientId.isNullOrEmpty()) {

            loading(true)
            when (val clientResponse = clientDetailUseCase(clientId)) {
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

    private fun getProductById(productId: String?) = viewModelScope.launch {
        if (!productId.isNullOrEmpty()) {
            loading(true)
            when (val productResponse = productsGetByIdUseCase(productId)) {
                is Resource.Failure -> {
                    setErrorMsg(productResponse.exception.message)
                }

                is Resource.Success -> {
                    addProduct(productResponse.result)
                }
            }
            loading(false)
        }

    }

    private fun addProduct(newProduct: Product) {
        val list = _uiState.value.cartProducts.toMutableList()
        val productList = list.find { it.productId == newProduct.productId }
        if (productList != null) {
            updateStock(newProduct, true)
        } else {
            newProduct.auxStock = newProduct.auxStock + 1
            _uiState.update {
                it.copy(
                    cartProducts = it.cartProducts.plus(newProduct),
                )
            }
            sumTotalSale()
        }
    }

    private fun removeProduct(productId: String?) {
        Log.e("TAG", "productId: $productId")
        productId?.let { id ->
            val list = _uiState.value.cartProducts.toMutableList()
            val productList = list.find { it.productId == id }
            Log.e("TAG", "productId removeProduct: $productList")
            productList?.let { product ->
                subtractTotalSale(product)
                val indexItem = list.indexOf(productList)
                list.remove(list[indexItem])
                _uiState.update {
                    it.copy(
                        cartProducts = list,
                    )
                }
            }

        }
    }


    private fun updateStock(newProduct: Product, incrementValue: Boolean) {
        val list = _uiState.value.cartProducts.toMutableList()
        val productList = list.find { it.productId == newProduct.productId }
        if (productList != null) {
            val validationIncrementValue = productList.auxStock < newProduct.inStock
            val validationDecrementValue = productList.auxStock > 0
            val executeValidation =
                if (incrementValue) validationIncrementValue else validationDecrementValue
            if (executeValidation) {
                val indexItem = list.indexOf(productList)
                productList.auxStock =
                    if (incrementValue) productList.auxStock + 1 else productList.auxStock - 1
                list[indexItem] = productList
                _uiState.update {
                    it.copy(
                        cartProducts = list,
                    )
                }
                if (incrementValue) {
                    sumTotalSale()
                } else {
                    subtractTotalSale(newProduct)
                }

            }
        }
    }

    private fun sumTotalSale() {
        _uiState.update {
            it.copy(
                totalSale = it.cartProducts.sumOf { product -> product.productPriceSale * product.auxStock }
            )
        }
    }

    private fun subtractTotalSale(product: Product) {
        _uiState.update {
            it.copy(
                totalSale = if (it.totalSale != null) it.totalSale - (product.productPriceSale) else it.totalSale
            )
        }
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError) }
    }

    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }
}