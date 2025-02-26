package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.GetClientsUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(private val clientsUseCase: GetClientsUseCase) :
    BaseViewModel() {

    private val _clientsFlow = MutableStateFlow<Resource<List<Client>>?>(Resource.Loading)
    val clientsFlow: StateFlow<Resource<List<Client>>?> = _clientsFlow

    init {
        getClients()
    }

    private fun getClients() = viewModelScope.launch {
        _clientsFlow.value = Resource.Loading
        val result = clientsUseCase()
        _clientsFlow.value = result
    }
}