package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.GetFinancesUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancesViewModel @Inject constructor(private val financesUseCase: GetFinancesUseCase) :
    BaseViewModel() {

    private val _financesFlow = MutableStateFlow<Resource<List<Sale>>?>(Resource.Loading)
    val financesFlow: StateFlow<Resource<List<Sale>>?> = _financesFlow

    init {
        getFinances()
    }

    private fun getFinances() = viewModelScope.launch {
        _financesFlow.value = Resource.Loading
        val result = financesUseCase()
        _financesFlow.value = result
    }
}