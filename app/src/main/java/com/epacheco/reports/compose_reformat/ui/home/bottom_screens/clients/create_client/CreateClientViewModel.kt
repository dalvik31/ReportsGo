package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.create_client

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.clients.CreateClientUseCase
import com.epacheco.reports.compose_reformat.domain.clients.DeleteClientUseCase
import com.epacheco.reports.compose_reformat.domain.clients.GetClientDetailUseCase
import com.epacheco.reports.compose_reformat.domain.clients.UpdateClientUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.Constants
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
class CreateClientViewModel @Inject constructor(
    private val getClientDetailUseCase: GetClientDetailUseCase,
    private val updateClientUseCase: UpdateClientUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
    private val createClientUseCase: CreateClientUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(CreateClientUiState())
    val uiState: StateFlow<CreateClientUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<CreateClientUiEffect>()
    val effectFlow: SharedFlow<CreateClientUiEffect> = _effectFlow


    fun handleIntent(intent: CreateClientUiIntent) {
        when (intent) {
            CreateClientUiIntent.CreateClient -> createClient()
            is CreateClientUiIntent.DeleteClient -> deleteClient(intent.clientId)
            CreateClientUiIntent.HideDialogs -> setErrorMsg()
            is CreateClientUiIntent.LoadClient -> getClient(intent.clientId)

            is CreateClientUiIntent.UpdateClient -> updateClient()
        }
    }

    private fun updateClient() = viewModelScope.launch {
        if (validateClientInputs()) {
            loading(true)
            when (val updateProductResponse =
                updateClientUseCase(
                    getNewClient(uiState.value.client)
                )) {
                is Resource.Failure ->
                    setErrorMsg(updateProductResponse.exception.message)

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successMessage = R.string.update_client_success)
                    _effectFlow.emit(CreateClientUiEffect.NavigateBack)
                }
            }
            loading(false)
        } else {
            setErrorMsg(app.getString(R.string.error_client_name_not_found))
        }


    }

    private fun createClient() = viewModelScope.launch {
        if (validateClientInputs()) {
            viewModelScope.launch {
                loading(true)
                when (val crateClientResponse =
                    createClientUseCase(getNewClient(null))) {
                    is Resource.Failure ->
                        setErrorMsg(crateClientResponse.exception.message)

                    is Resource.Success -> {
                        _uiState.value =
                            _uiState.value.copy(successMessage = R.string.msg_product_update_success)
                        _effectFlow.emit(CreateClientUiEffect.NavigateBack)
                    }
                }
                loading(false)
            }
        } else setErrorMsg(app.getString(R.string.error_client_name_not_found))

    }

    private fun validateClientInputs(): Boolean {
        var inputsClientValid = true
        if (_uiState.value.clientName.isEmpty()) {
            inputsClientValid = false
        }
        return inputsClientValid
    }


    private fun deleteClient(clientId: String) = viewModelScope.launch {
        loading(true)
        when (val deleteClientResponse = deleteClientUseCase.invoke(clientId)) {
            is Resource.Failure -> {
                setErrorMsg(deleteClientResponse.exception.message)
            }

            is Resource.Success -> {
                _uiState.value =
                    _uiState.value.copy(successMessage = R.string.client_delete_success)
                _effectFlow.emit(CreateClientUiEffect.NavigateBack)
            }
        }
        loading(false)
    }


    private fun getNewClient(client: Client?): Client {
        val clientId = DateUtils.now().toString()
        val clientDetail = client?.let { client } ?: run { Client() }
        return clientDetail.copy(
            id = client?.id ?: clientId,
            name = _uiState.value.clientName,
            lastNanme = _uiState.value.clientLastName,
            detail = _uiState.value.clientInfo,
            phone = _uiState.value.clientPhone,
            limit = _uiState.value.clientCredit.ifEmpty { Constants.LIMIT_AMOUNT }.toDouble(),
            dateClient = client?.dateClient ?: clientId,
        )
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
                setValuesToEdit(clientResponse.result)
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
        _uiState.update {
            it.copy(clientName = inputNames)
        }
    }

    fun onInputLastNameChanged(inputLastNames: String) {
        _uiState.update {
            it.copy(clientLastName = inputLastNames)
        }
    }

    fun onInputInfoChanged(inputInfo: String) {
        _uiState.update {
            it.copy(clientInfo = inputInfo)
        }
    }

    fun onInputPhoneChanged(inputPhone: String) {
        _uiState.update {
            it.copy(clientPhone = inputPhone)
        }
    }

    fun onInputCreditChanged(inputCredit: String) {
        _uiState.update {
            it.copy(clientCredit = inputCredit)
        }
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError, successMessage = null) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }

}