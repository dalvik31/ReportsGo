package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.domain.ClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.ClientUpdateDebtUseCase
import com.epacheco.reports.compose_reformat.domain.ClientUpdateLimitUseCase
import com.epacheco.reports.compose_reformat.domain.ProductUpdateStockUseCase
import com.epacheco.reports.compose_reformat.domain.ProductsGetByIdUseCase
import com.epacheco.reports.compose_reformat.domain.SaleCreateUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.DateUtils.FORMAT_DATE1
import com.epacheco.reports.tools.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val clientDetailUseCase: ClientDetailUseCase,
    private val productsGetByIdUseCase: ProductsGetByIdUseCase,
    private val saleCreateUseCase: SaleCreateUseCase,
    private val updateClientLimit: ClientUpdateLimitUseCase,
    private val productUpdateStockUseCase: ProductUpdateStockUseCase,
    private val clientUpdateDebtUseCase: ClientUpdateDebtUseCase
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
            is SalesUiIntent.IsCreditSale -> setIsCreditSale(intent.isCreditSale)
            is SalesUiIntent.SetNewLimit -> setNewLimit(intent.newLimit)
            is SalesUiIntent.SetNewLimitUsed -> setNewLimitUsed(intent.newLimitUsed)
            is SalesUiIntent.SaveSale -> saveSale(intent.isCreditSale)
            SalesUiIntent.HideDialogs -> setErrorMsg()
            SalesUiIntent.RemoveClient -> removeClient()
        }
    }


    private fun removeClient() {
        _uiState.update { it.copy(client = null) }
    }

    private fun setNewLimitUsed(newLimitUsed: Double) {
        _uiState.update { it.copy(newLimitUsed = newLimitUsed) }
    }

    private fun setNewLimit(newLimit: Double) {
        _uiState.update { it.copy(newLimit = newLimit) }
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

    private fun setIsCreditSale(isCreditSale: Boolean) {
        _uiState.update {
            it.copy(
                isCreditSale = isCreditSale
            )
        }
    }

    private fun getSaleDetail(product: Product): Sale {

        val saleId = System.currentTimeMillis().toString()
        return Sale(
            saleId = DateUtils.format(saleId.toLong(), FORMAT_DATE1),
            creditSale = uiState.value.isCreditSale,
            idClient = uiState.value.client?.id ?: Constants.ID_GENERIC_SALES,
            nameClient = uiState.value.client?.name ?: "",
            imgProduct = product.urlImage,
            productName = product.productName,
            productPriceBuy = product.productPriceBuy,
            productPriceSale = product.productPriceSale,
            productId = product.productId,
            saleDate = saleId,
        )
    }

    private fun saveSale(isCreditSale: Boolean) = viewModelScope.launch {
        val listProducts = uiState.value.cartProducts
        listProducts.forEachIndexed { index, product ->
            loading(true)
            when (val saleCreateResponse = saleCreateUseCase(getSaleDetail(product))) {
                is Resource.Failure -> {
                    setErrorMsg(saleCreateResponse.exception.message)
                }

                is Resource.Success -> {
                    val newStock = product.inStock - product.auxStock
                    when (val updateStockProductResponse =
                        productUpdateStockUseCase(productId = product.productId, newStock)) {
                        is Resource.Failure -> {
                            setErrorMsg(updateStockProductResponse.exception.message)
                        }

                        is Resource.Success -> {}
                    }


                    if (isCreditSale && (index + 1) == listProducts.size) {
                        val clientId: String =
                            uiState.value.client?.id ?: Constants.ID_GENERIC_SALES
                        val newLimit = uiState.value.newLimit
                        val newLimitUsed = uiState.value.newLimitUsed
                        val newDebt =
                            uiState.value.client?.debt?.plus(uiState.value.totalSale ?: 0.0)



                        when (val updateClientLimitResponse = updateClientLimit(
                            clientId = clientId,
                            newLimit = newLimit,
                            newLimitUsed = newLimitUsed
                        )) {
                            is Resource.Failure -> {
                                setErrorMsg(updateClientLimitResponse.exception.message)
                            }

                            is Resource.Success -> {


                                when (val clientUpdateDebtUseCase = clientUpdateDebtUseCase(
                                    clientId = clientId,
                                    newDebt = newDebt ?: 0.0
                                )) {
                                    is Resource.Failure -> {
                                        setErrorMsg(clientUpdateDebtUseCase.exception.message)
                                    }

                                    is Resource.Success -> {
                                        resetValues()
                                    }
                                }
                            }
                        }
                    } else {
                        resetValues()
                    }


                }
            }
        }
        loading(false)
    }


    private fun resetValues() {
        _uiState.update {
            it.copy(
                successOperationMsg = R.string.msg_sale_update,
                cartProducts = emptyList(),
                totalSale = null,
                isCreditSale = false,
                product = null
            )
        }
        getClientById(uiState.value.client?.id)
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successOperationMsg = null) }
    }

    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }
}