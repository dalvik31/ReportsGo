package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.epacheco.reports.compose_reformat.domain.GetProductsUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsUseCase: GetProductsUseCase, private val productsRepository: ProductsRepository) :
    BaseViewModel() {

    private val _productsFlow = MutableStateFlow<Resource<List<Product>>?>(null)
    val productsFlow: StateFlow<Resource<List<Product>>?> = _productsFlow

    init {
        //getProducts()
    }


    private fun getProducts() = viewModelScope.launch {
        //_productsFlow.value = Resource.Waiting

       productsUseCase().collect {
            it?.let {
                _productsFlow.value = Resource.Success(it)
            } ?: run {
                Resource.Failure(Exception("Mi expetion"))
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