package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import androidx.lifecycle.viewModelScope
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.domain.ProductCreateUseCase
import com.epacheco.reports.compose_reformat.domain.ProductImgDeleteUseCase
import com.epacheco.reports.compose_reformat.domain.ProductDeleteUseCase
import com.epacheco.reports.compose_reformat.domain.FirebaseUploadImgProductUseCase
import com.epacheco.reports.compose_reformat.domain.ProductsGetByIdUseCase
import com.epacheco.reports.compose_reformat.domain.ProductUpdateUseCase
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
    private val productsByIdUseCase: ProductsGetByIdUseCase,
    private val firebaseUploadImgProductUseCase: FirebaseUploadImgProductUseCase,
    private val productUpdateUseCase: ProductUpdateUseCase,
    private val productCreateUseCase: ProductCreateUseCase,
    private val productDeleteUseCase: ProductDeleteUseCase,
    private val productImgDeleteUseCase: ProductImgDeleteUseCase,
    private val app: ReportsApp
) :
    BaseViewModel() {

    private val _inputProductName = MutableStateFlow("")
    val inputProductName: StateFlow<String> = _inputProductName

    private val _inputProductDescription = MutableStateFlow("")
    val inputProductDescription: StateFlow<String> = _inputProductDescription

    private val _inputProductBuyPrice = MutableStateFlow("")
    val inputProductBuyPrice: StateFlow<String> = _inputProductBuyPrice

    private val _inputProductSellPrice = MutableStateFlow("")
    val inputProductSellPrice: StateFlow<String> = _inputProductSellPrice

    private val _inputProductSize = MutableStateFlow("")
    val inputProductSize: StateFlow<String> = _inputProductSize

    private val _isProductSizeNumeric = MutableStateFlow(false)
    val isProductSizeNumeric: StateFlow<Boolean> = _isProductSizeNumeric

    private val _inputProductColor = MutableStateFlow("")
    val inputProductColor: StateFlow<String> = _inputProductColor

    private val _inputProductColorCode = MutableStateFlow("")
    val inputProductColorCode: StateFlow<String> = _inputProductColorCode

    private val _inputProductGender = MutableStateFlow("")
    val inputProductGender: StateFlow<String> = _inputProductGender

    private val _inputProductStock = MutableStateFlow("")
    val inputProductStock: StateFlow<String> = _inputProductStock

    private val _inputProductCode = MutableStateFlow("")
    val inputProductCode: StateFlow<String> = _inputProductCode

    private val _inputProductUrlImg = MutableStateFlow("")
    val inputProductUrlImg: StateFlow<String> = _inputProductUrlImg

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    private val _effectFlow = MutableSharedFlow<ProductDetailUiEffect>()
    val effectFlow: SharedFlow<ProductDetailUiEffect> = _effectFlow

    fun handleIntent(intent: ProductDetailUiIntent) {
        when (intent) {
            ProductDetailUiIntent.Error -> setErrorMsg()
            is ProductDetailUiIntent.LoadProduct -> getProductById(intent.productId)
            ProductDetailUiIntent.HideDialogs -> setErrorMsg()
            ProductDetailUiIntent.CreateProduct -> validateInfoToCreateProduct()

            is ProductDetailUiIntent.DeleteProduct -> deleteProduct(intent.productId)
            is ProductDetailUiIntent.SetImageFile -> setImageFile(intent.imgFile)
            is ProductDetailUiIntent.UpdateProduct -> validateInfoToUpdateProduct(intent.productId)
        }
    }

    /**
     *
     * Method to search a product by id
     *
     * */
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


    /**
     *
     * Methods to create a new product
     *
     * */
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
                    firebaseUploadImgProductUseCase(
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
            when (val updateProductResponse =
                productCreateUseCase(getNewProduct())) {
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


    /**
     *
     * Methods to update a product
     *
     * */
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
                productUpdateUseCase(
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

        if (!validatePrice()) {
            setErrorMsg(app.getString(R.string.msg_product_inputs_different_prices))
            return false
        }

        return true
    }


    private fun deleteProduct(productId: String) = viewModelScope.launch {
        loading(true)
        when (val deleteProductResponse = productDeleteUseCase.invoke(productId)) {
            is Resource.Failure -> {
                setErrorMsg(deleteProductResponse.exception.message)
            }
            is Resource.Success -> {
                productImgDeleteUseCase.invoke(getNameImage())
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
        val name = _inputProductName.value
        val desc = _inputProductDescription.value
        val size = _inputProductSize.value
        val col = _inputProductColor.value
        val img = _inputProductUrlImg.value
        val gen = _inputProductGender.value
        val cod = _inputProductCode.value
        val stock = _inputProductStock.value
        val bPri = _inputProductBuyPrice.value
        val sPri = _inputProductSellPrice.value
        return (name.isNotEmpty() && desc.isNotEmpty() && size.isNotEmpty() && col.isNotEmpty() &&
                gen.isNotEmpty() && img.isNotEmpty() && cod.isNotEmpty() && stock
            .isNotEmpty() && bPri.isNotEmpty() && sPri.isNotEmpty())
    }

    private fun validatePrice(): Boolean {
        return _inputProductSellPrice.value.toDouble() > _inputProductBuyPrice.value.toDouble()
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
            productName = _inputProductName.value,
            productColor = _inputProductColor.value,
            productColorCode = _inputProductColorCode.value,
            productCode = _inputProductCode.value,
            productDate = DateUtils.now().toString(),
            productSize = _inputProductSize.value,
            productType = _inputProductGender.value,
            productDescription = _inputProductDescription.value,
            productPriceBuy = _inputProductBuyPrice.value.toDouble(),
            productPriceSale = _inputProductSellPrice.value.toDouble(),
            productSizeNumeric = _isProductSizeNumeric.value,
            inStock = _inputProductStock.value.toInt(),
            urlImage = _inputProductUrlImg.value
        )

    fun onInputNameChanged(inputName: String) {
        _inputProductName.value = inputName
    }

    fun onInputDescriptionChanged(inputDescription: String) {
        _inputProductDescription.value = inputDescription
    }

    fun onInputBuyPriceChanged(inputBuyPrice: String) {
        _inputProductBuyPrice.value = inputBuyPrice
    }

    fun onInputSellPriceChanged(inputSellPrice: String) {
        _inputProductSellPrice.value = inputSellPrice
    }

    fun onInputSizeChanged(inputSize: String) {
        _inputProductSize.value = inputSize
    }

    fun onInputIsNumericSizeChanged(inputIsNumericSize: Boolean) {
        _isProductSizeNumeric.value = inputIsNumericSize
    }

    fun onInputColorChanged(inputColor: String) {
        _inputProductColor.value = inputColor
    }

    fun onInputColorCodeChanged(inputColorCode: String) {
        _inputProductColorCode.value = inputColorCode
    }

    fun onInputGenderChanged(inputGender: String) {
        _inputProductGender.value = inputGender
    }

    fun onInputStockChanged(inputStock: String) {
        _inputProductStock.value = inputStock
    }

    fun onInputCodeChanged(inputCode: String) {
        _inputProductCode.value = inputCode
    }

    fun onInputImgUrlChanged(urlImg: String) {
        _inputProductUrlImg.value = urlImg
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