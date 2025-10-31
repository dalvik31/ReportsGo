package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.domain.ClientCreateUseCase
import com.epacheco.reports.compose_reformat.domain.ClientDeleteUseCase
import com.epacheco.reports.compose_reformat.domain.ClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.ClientUpdateUseCase
import com.epacheco.reports.compose_reformat.domain.FinancesGetByClientIdUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.ClientDetailUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.ClientDetailUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.DetailClientUiState
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product.ProductDetailUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product.ProductDetailUiIntent
import com.epacheco.reports.compose_reformat.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailClientViewModel @Inject constructor(
    private val clientDetailUseCase: ClientDetailUseCase,
    private val clientUpdateUseCase: ClientUpdateUseCase,
    private val clientDeleteUseCase: ClientDeleteUseCase,
    private val clientCreateUseCase: ClientCreateUseCase,

) :
    BaseViewModel() {

    private val _inputClientNames = MutableStateFlow("")
    val inputClientNames: StateFlow<String> = _inputClientNames

    private val _inputClientLastName = MutableStateFlow("")
    val inputClientLastName: StateFlow<String> = _inputClientLastName

    private val _inputClientInfo = MutableStateFlow("")
    val inputClientInfo: StateFlow<String> = _inputClientInfo

    private val _inputClientPhone = MutableStateFlow("")
    val inputClientPhone: StateFlow<String> = _inputClientPhone

    private val _inputClientCredit = MutableStateFlow("")
    val inputClientCredit: StateFlow<String> = _inputClientCredit


    private val _uiState = MutableStateFlow(DetailClientUiState())
    val uiState: StateFlow<DetailClientUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<ClientDetailUiEffect>()
    val effectFlow: SharedFlow<ClientDetailUiEffect> = _effectFlow


    fun handleIntent(intent: ClientDetailUiIntent) {
        when (intent) {
            ClientDetailUiIntent.CreateClient -> createClient()
            is ClientDetailUiIntent.DeleteClient -> deleteClient(intent.clientId)
            ClientDetailUiIntent.HideDialogs -> setErrorMsg()
            is ClientDetailUiIntent.LoadClient -> getClientDetail(
                intent.clientId,
                intent.isEditMode
            )

            is ClientDetailUiIntent.UpdateClient -> updateClient()
        }
    }

    private fun updateClient() = viewModelScope.launch {
        loading(true)
        when (val updateProductResponse =
            clientUpdateUseCase(
                getNewClient(uiState.value.clientDetail)
            )) {
            is Resource.Failure ->
                setErrorMsg(updateProductResponse.exception.message)

            is Resource.Success -> {
                _uiState.value =
                    _uiState.value.copy(successMessage = R.string.update_client_success)
                _effectFlow.emit(ClientDetailUiEffect.NavigateBack)
            }
        }
        loading(false)

    }

    private fun createClient() = viewModelScope.launch {
        viewModelScope.launch {
            loading(true)
            when (val crateClientResponse =
                clientCreateUseCase(getNewClient(null))) {
                is Resource.Failure ->
                    setErrorMsg(crateClientResponse.exception.message)

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successMessage = R.string.msg_product_update_success)
                    _effectFlow.emit(ClientDetailUiEffect.NavigateBack)
                }
            }
            loading(false)
        }
    }


    private fun deleteClient(clientId: String) = viewModelScope.launch {
        loading(true)
        when (val deleteClientResponse = clientDeleteUseCase.invoke(clientId)) {
            is Resource.Failure -> {
                setErrorMsg(deleteClientResponse.exception.message)
            }

            is Resource.Success -> {
                _uiState.value =
                    _uiState.value.copy(successMessage = R.string.client_delete_success)
                _effectFlow.emit(ClientDetailUiEffect.NavigateBack)
            }
        }
        loading(false)
    }


    private fun getNewClient(client: Client?): Client {
        val clientId = DateUtils.now().toString()
        val clientDetail = client?.let { client } ?: run { Client() }
        return clientDetail.copy(
            id = client?.id ?: clientId,
            name = _inputClientNames.value,
            lastNanme = _inputClientLastName.value,
            detail = _inputClientInfo.value,
            phone = _inputClientPhone.value,
            limit = _inputClientCredit.value.toDouble(),
            dateClient = client?.dateClient ?: clientId,
        )
    }


    fun getClientDetail(clientId: String, isEditMode: Boolean = false) = viewModelScope.launch {
        loading(true)
        when (val clientResponse = clientDetailUseCase(clientId)) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        clientDetail = clientResponse.result
                    )
                }
                if (isEditMode) {
                    setValuesToEdit(clientResponse.result)
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

    private fun setValuesToEdit(client: Client) {
        client.also {
            onInputNameChanged(it.name)
            onInputLastNameChanged(it.lastNanme)
            onInputInfoChanged(it.detail)
            onInputPhoneChanged(it.phone)
            onInputCreditChanged(it.limit.toString())
        }
    }

    fun onInputNameChanged(inputNames: String) {
        _inputClientNames.value = inputNames
    }

    fun onInputLastNameChanged(inputLastNames: String) {
        _inputClientLastName.value = inputLastNames
    }

    fun onInputInfoChanged(inputInfo: String) {
        _inputClientInfo.value = inputInfo
    }

    fun onInputPhoneChanged(inputPhone: String) {
        _inputClientPhone.value = inputPhone
    }

    fun onInputCreditChanged(inputCredit: String) {
        _inputClientCredit.value = inputCredit
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successMessage = null) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

}