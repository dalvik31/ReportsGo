package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.finances.GetUserFinancesUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancesViewModel @Inject constructor(private val getUserFinancesUseCase: GetUserFinancesUseCase) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(FinancesUiState())
    val uiState: StateFlow<FinancesUiState> = _uiState

    fun handleIntent(intent: FinancesUiIntent) {
        when (intent) {
            FinancesUiIntent.LoadFinancesItems -> loadFinancesItems()
            is FinancesUiIntent.SetFinalDate -> setFinalDate(intent.finalDate)
            is FinancesUiIntent.SetInitialDate -> setInitialDate(intent.initialDate)
        }

    }

    private fun loadFinancesItems() =
        viewModelScope.launch {
            loading(true)
            when (val financesItemsResponses = getUserFinancesUseCase.invoke(
                uiState.value.initialDate, uiState.value.finalDate,
            )) {
                is Resource.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = financesItemsResponses.exception.message
                    )
                }

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(
                            financesList = financesItemsResponses.result,
                        )
                }
            }
            loading(false)
        }


    private fun setInitialDate(initialDate: Long) {
        _uiState.value =
            _uiState.value.copy(initialDate = initialDate)

    }

    private fun setFinalDate(finalDate: Long) {
        _uiState.value =
            _uiState.value.copy(finalDate = finalDate)

    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.value = _uiState.value.copy(errorMessage = msgError)
    }

    override fun loading(showLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = showLoading)
    }

}

