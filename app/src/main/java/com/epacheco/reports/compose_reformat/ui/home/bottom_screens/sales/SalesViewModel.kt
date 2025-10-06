package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.FinancesUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(private val financesUseCase: FinancesUseCase) :
    BaseViewModel() {

    private val _financesFlow = MutableStateFlow<Resource<List<Sale>>?>(null)
    val financesFlow: StateFlow<Resource<List<Sale>>?> = _financesFlow

    init {
        //getFinances()
    }

    private fun getFinances() = viewModelScope.launch {
        //_financesFlow.value = Resource.Waiting
        val result = financesUseCase()
        _financesFlow.value = result
    }

    override fun setErrorMsg(msgError: String?) {
        TODO("Not yet implemented")
    }

    override fun loading(showLoading: Boolean) {
        TODO("Not yet implemented")
    }
}