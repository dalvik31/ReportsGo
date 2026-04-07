package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.client_detail

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.clients.GetClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.clients.UpdateDebtClientUseCase
import com.epacheco.reports.compose_reformat.domain.sales.CreateSaleUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.Finances.PaymentType
import com.epacheco.reports.compose_reformat.model.sales.Sale
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
class DetailClientViewModel @Inject constructor(
    private val getClientDetailUseCase: GetClientDetailUseCase,
    private val updateDebtClientUseCase: UpdateDebtClientUseCase,
    private val createSaleUseCase: CreateSaleUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(DetailClientUiState())
    val uiState: StateFlow<DetailClientUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<ClientDetailUiEffect>()
    val effectFlow: SharedFlow<ClientDetailUiEffect> = _effectFlow


    fun handleIntent(intent: ClientDetailUiIntent) {
        when (intent) {
            ClientDetailUiIntent.HideDialogs -> setErrorMsg()
            is ClientDetailUiIntent.LoadClient -> getClient(intent.clientId)

            is ClientDetailUiIntent.UpdateAmountPayClient -> updateAmountPayClient(intent.clientId)
        }
    }

    fun getClient(clientId: String) = viewModelScope.launch {
        loading(true)
        when (val clientResponse = getClientDetailUseCase(clientId)) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        client = clientResponse.result
                    )
                }
            }

            is Resource.Failure -> {
                _uiState.update {
                    it.copy(
                        errorMessage = clientResponse.exception.message
                    )
                }
            }
        }
        loading(false)
    }

    private fun validatePaymentInputs(): Boolean {
        var inputsPaymentsValid = true
        if (_uiState.value.clientAmount.isEmpty()) {
            inputsPaymentsValid = false
        }

        return inputsPaymentsValid
    }

    fun updateAmountPayClient(clientId: String) = viewModelScope.launch {
        if (!validatePaymentInputs()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Debes ingresar un monto"
                )
            }
            return@launch
        }
        loading(true)
        val currentDebt = (_uiState.value.client?.debt) ?: 0.0
        var newDebt = currentDebt - (_uiState.value.clientAmount.toDouble())
        when (val clientUpdateDebtResponse =
            updateDebtClientUseCase(clientId, newDebt = newDebt)) {

            is Resource.Success -> {
                val saleId = DateUtils.now().toString()
                when (val createSaleResponse =
                    createSaleUseCase(
                        saleDetail = Sale(
                            saleId = DateUtils.dateFormat(saleId, DateUtils.FORMAT_DATE1),
                            idClient = clientId,
                            saleConcept = _uiState.value.clientConcept.ifEmpty { app.getString(R.string.client_debt_without_concept) },
                            paymentType = PaymentType.PAY,
                            productPriceSale = _uiState.value.clientAmount.toDouble(),
                            productPriceBuy = 0.0,
                            saleDate = saleId,
                            productName = _uiState.value.clientConcept.ifEmpty { app.getString(R.string.client_debt_without_concept) },
                            nameClient = uiState.value.client?.name ?: "",
                        )
                    )) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                successMessage = R.string.msg_client_amount_update_success,
                                clientAmount = "",
                                clientConcept = ""
                            )
                        }
                        getClient(clientId)
                    }

                    is Resource.Failure -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = createSaleResponse.exception.message
                            )
                        }
                    }
                }
            }

            is Resource.Failure -> {
                _uiState.update {
                    it.copy(
                        errorMessage = clientUpdateDebtResponse.exception.message
                    )
                }
            }
        }
        loading(false)
    }


    fun onInputAmountChanged(inputAmount: String) {
        _uiState.update {
            it.copy(clientAmount = inputAmount)
        }
    }

    fun onInputConceptChanged(inputConcept: String) {
        _uiState.update {
            it.copy(clientConcept = inputConcept)
        }
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successMessage = null) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

}