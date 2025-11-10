package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.products.CreateProductUseCase
import com.epacheco.reports.compose_reformat.domain.products.DeleteProductImgUseCase
import com.epacheco.reports.compose_reformat.domain.products.DeleteProductUseCase
import com.epacheco.reports.compose_reformat.domain.products.GetProductByIdUseCase
import com.epacheco.reports.compose_reformat.domain.products.UpdateProductUseCase
import com.epacheco.reports.compose_reformat.domain.products.UploadImgProductUseCase
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.ui.base.BaseViewModel
import com.epacheco.reports.compose_reformat.utils.DateUtils
import com.epacheco.reports.compose_reformat.utils.extensions.compress
import com.epacheco.reports.compose_reformat.utils.extensions.getNameProductImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productsByIdUseCase: GetProductByIdUseCase,
    private val uploadImgProductUseCase: UploadImgProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val deleteProductImgUseCase: DeleteProductImgUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<ProductDetailUiEffect>()
    val effectFlow: SharedFlow<ProductDetailUiEffect> = _effectFlow

    fun handleIntent(intent: ProductDetailUiIntent) {
        when (intent) {
            is ProductDetailUiIntent.LoadProduct -> getProductById(intent.productId)
            ProductDetailUiIntent.HideDialogs -> setErrorMsg()
            ProductDetailUiIntent.CreateProduct -> validateInfoToCreateProduct()
            is ProductDetailUiIntent.DeleteProduct -> deleteProduct(intent.productId)
            is ProductDetailUiIntent.SetImageFile -> setImageFile(intent.imgFile)
            is ProductDetailUiIntent.UpdateProduct -> validateInfoToUpdateProduct(intent.productId)
        }
    }

    private fun getProductById(productId: String) = viewModelScope.launch {
        loading(true)
        when (val productsByIdResponse = productsByIdUseCase.invoke(productId)) {
            is Resource.Failure -> {
                setErrorMsg(productsByIdResponse.exception.message)
            }

            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        product = productsByIdResponse.result
                    )
                }
                setValuesToEdit(productsByIdResponse.result)
            }
        }
        loading(false)
    }

    private fun validateInfoToCreateProduct() {
        if (validateInputs()) {
            uploadProductImg()
        }
    }

    private fun uploadProductImg(productId: String? = null) {
        _uiState.value.newFileImg?.let { imgFile ->
            viewModelScope.launch {
                loading(true)
                when (val uploadImageResponse =
                    uploadImgProductUseCase(
                        imgFile.compress(app), nameImgToReplace = getNameImage()
                    )) {
                    is Resource.Failure ->
                        setErrorMsg(uploadImageResponse.exception.message)

                    is Resource.Success -> {
                        onInputImgUrlChanged(uploadImageResponse.result.toString())
                        productId?.let {
                            updateProduct(it)
                        } ?: run {
                            createProduct()
                        }

                    }
                }
                loading(false)
            }
        } ?: run {
            setErrorMsg(app.getString(R.string.msg_error))
        }
    }

    private fun createProduct() {
        viewModelScope.launch {
            loading(true)
            when (val createProductResponse =
                createProductUseCase(getNewProduct())) {
                is Resource.Failure ->
                    setErrorMsg(createProductResponse.exception.message)

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successMessage = R.string.msg_product_update_success)
                    _effectFlow.emit(ProductDetailUiEffect.NavigateBack)
                }
            }
            loading(false)
        }
    }

    private fun validateInfoToUpdateProduct(productId: String) {
        if (validateInputs()) {
            if (changeImageProduct()) {
                uploadProductImg(productId)
            } else {
                updateProduct(productId)
            }
        }
    }

    private fun updateProduct(productId: String) {
        viewModelScope.launch {
            loading(true)
            when (val updateProductResponse =
                updateProductUseCase(
                    getNewProduct().copy(
                        productId = productId
                    )
                )) {
                is Resource.Failure ->
                    setErrorMsg(updateProductResponse.exception.message)

                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(successMessage = R.string.msg_product_update_success)
                    _effectFlow.emit(ProductDetailUiEffect.NavigateBack)
                }
            }
            loading(false)
        }
    }

    private fun validateInputs(): Boolean {
        if (!validInputs()) {
            setErrorMsg(app.getString(R.string.order_empty_inputs_error))
            return false
        }

        return true
    }


    private fun deleteProduct(productId: String) = viewModelScope.launch {
        loading(true)
        when (val deleteProductResponse = deleteProductUseCase.invoke(productId)) {
            is Resource.Failure -> {
                setErrorMsg(deleteProductResponse.exception.message)
            }

            is Resource.Success -> {
                deleteProductImgUseCase.invoke(getNameImage())
                _uiState.value =
                    _uiState.value.copy(successMessage = R.string.msg_product_delete_success)
                _effectFlow.emit(ProductDetailUiEffect.NavigateBack)
            }
        }
        loading(false)
    }


    private fun changeImageProduct(): Boolean {
        return _uiState.value.newFileImg != null
    }

    private fun getNameImage(): String? {
        return _uiState.value.product?.urlImage?.getNameProductImage()
    }

    private fun validInputs(): Boolean {
        uiState.value.run {
            val name = productName
            val desc = productDescription
            val size = productSize
            val col = productColor
            val img = productUrlImg
            val gen = productGender
            val cod = productCode
            val stock = productStock
            val bPri = productBuyPrice
            val sPri = productSellPrice
            return (name.isNotEmpty() && desc.isNotEmpty() && size.isNotEmpty() && col.isNotEmpty() &&
                    gen.isNotEmpty() && img.isNotEmpty() && cod.isNotEmpty() && stock
                .isNotEmpty() && bPri.isNotEmpty() && sPri.isNotEmpty())
        }
    }

    private fun setValuesToEdit(product: Product) {
        product.also {
            onInputNameChanged(it.productName)
            onInputDescriptionChanged(it.productDescription)
            onInputBuyPriceChanged(it.productPriceBuy.toString())
            onInputSellPriceChanged(it.productPriceSale.toString())
            onInputSizeChanged(it.getSize())
            onInputIsNumericSizeChanged(it.productSizeNumeric)
            onInputColorChanged(it.productColor)
            onInputColorCodeChanged(it.productColorCode)
            onInputGenderChanged(it.productType)
            onInputStockChanged(it.inStock.toString())
            onInputCodeChanged(it.productCode)
            onInputImgUrlChanged(it.urlImage)
        }
    }

    private fun getNewProduct(): Product =
        Product(
            productId = DateUtils.now().toString(),
            productName = uiState.value.productName,
            productColor = uiState.value.productColor,
            productColorCode = uiState.value.productColorCode,
            productCode = uiState.value.productCode,
            productDate = DateUtils.now().toString(),
            productSize = uiState.value.productSize,
            productType = uiState.value.productGender,
            productDescription = uiState.value.productDescription,
            productPriceBuy = uiState.value.productBuyPrice.toDouble(),
            productPriceSale = uiState.value.productSellPrice.toDouble(),
            productSizeNumeric = uiState.value.isProductSizeNumeric,
            inStock = uiState.value.productStock.toInt(),
            urlImage = uiState.value.productUrlImg
        )

    fun onInputNameChanged(inputName: String) {
        _uiState.update { it.copy(productName = inputName) }
    }

    fun onInputDescriptionChanged(inputDescription: String) {
        _uiState.update { it.copy(productDescription = inputDescription) }
    }

    fun onInputBuyPriceChanged(inputBuyPrice: String) {
        _uiState.update { it.copy(productBuyPrice = inputBuyPrice) }
    }

    fun onInputSellPriceChanged(inputSellPrice: String) {
        _uiState.update { it.copy(productSellPrice = inputSellPrice) }
    }

    fun onInputSizeChanged(inputSize: String) {
        _uiState.update { it.copy(productSize = inputSize) }
    }

    fun onInputIsNumericSizeChanged(inputIsNumericSize: Boolean) {
        _uiState.update { it.copy(isProductSizeNumeric = inputIsNumericSize) }
    }

    fun onInputColorChanged(inputColor: String) {
        _uiState.update { it.copy(productColor = inputColor) }
    }

    fun onInputColorCodeChanged(inputColorCode: String) {
        _uiState.update { it.copy(productColorCode = inputColorCode) }
    }

    fun onInputGenderChanged(inputGender: String) {
        _uiState.update { it.copy(productGender = inputGender) }
    }

    fun onInputStockChanged(inputStock: String) {
        _uiState.update { it.copy(productStock = inputStock) }
    }

    fun onInputCodeChanged(inputCode: String) {
        _uiState.update { it.copy(productCode = inputCode) }
    }

    fun onInputImgUrlChanged(urlImg: String) {
        _uiState.update { it.copy(productUrlImg = urlImg) }
    }

    private fun setImageFile(imgFile: File) {
        onInputImgUrlChanged(imgFile.toString())
        _uiState.update { it.copy(newFileImg = imgFile) }
    }

    override fun setErrorMsg(msgError: String?) {
        _uiState.update { it.copy(errorMessage = msgError) }
    }


    override fun loading(showLoading: Boolean) {
        _uiState.update { it.copy(isLoading = showLoading) }
    }
}