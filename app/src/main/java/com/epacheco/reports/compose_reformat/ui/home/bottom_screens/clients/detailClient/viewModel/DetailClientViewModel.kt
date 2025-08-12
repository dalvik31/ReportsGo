package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.GetClientDetailUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.DetailClientUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailClientViewModel @Inject constructor(private val clientDetailUseCase: GetClientDetailUseCase): BaseViewModel()  {

    private val _clientFlow = MutableStateFlow(DetailClientUiState())
    val clientFlow: StateFlow<DetailClientUiState> = _clientFlow

    fun getClientDetail(clientId: String) = viewModelScope.launch {
        Log.e("aqui", "Entro ViewModel: $clientId")
        when (val clientResponse = clientDetailUseCase(clientId)) {
            is Resource.Success -> {
                Log.e("aqui", "ClientDetailViewModel SUCCESSS: ${clientResponse.result}")
                _clientFlow.update {
                    it.copy(
                        clientDetail = clientResponse.result
                    )
                }
            }

            is Resource.Failure -> {
                _clientFlow.update {
                    it.copy(
                        errorMessage = clientResponse.exception.message
                    )
                }
                Log.e("aqui", "ERRORRRR: message: ${clientResponse.exception} ")
            }
        }
    }

    override fun setErrorMsg(msgError: String?) {
        TODO("Not yet implemented")
    }

    override fun loading(showLoading: Boolean) {
        TODO("Not yet implemented")
    }
}