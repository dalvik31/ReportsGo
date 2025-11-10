package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import com.epacheco.reports.compose_reformat.model.products.Product
import java.io.File


data class ProductDetailUiState(
    val product: Product? = null,
    val productName: String = "",
    val productDescription: String = "",
    val productBuyPrice: String = "",
    val productSellPrice: String = "",
    val productSize: String = "",
    val isProductSizeNumeric: Boolean = false,
    val productColor: String = "",
    val productColorCode: String = "",
    val productGender: String = "",
    val productStock: String = "",
    val productCode: String = "",
    val productUrlImg: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val newFileImg: File? = null,
    val successMessage: Int? = null,
)