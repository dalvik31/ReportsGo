package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsSuccessDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker.PickerPictureDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiEffect
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order.NewOrderUiIntent
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest
import kotlin.text.ifEmpty


@Composable
fun NewProductScreen(
    newProductViewModel: ProductDetailViewModel = hiltViewModel<ProductDetailViewModel>(),
    productToEdit: String? = null,
    onBackPressed: (() -> Unit)? = null,
) {

    var showProfilePictureDialog by remember { mutableStateOf(false) }
    var showDialogConfirmDeleteProduct by remember { mutableStateOf(false) }

    val uiState by newProductViewModel.uiState.collectAsState()
    val inputName by newProductViewModel.inputProductName.collectAsState()
    val inputDescription by newProductViewModel.inputProductDescription.collectAsState()
    val inputBuyPrice by newProductViewModel.inputProductBuyPrice.collectAsState()
    val inputSellPrice by newProductViewModel.inputProductSellPrice.collectAsState()
    val inputSize by newProductViewModel.inputProductSize.collectAsState()
    val isNumericSize by newProductViewModel.isProductSizeNumeric.collectAsState()
    val inputColor by newProductViewModel.inputProductColor.collectAsState()
    val inputColorCode by newProductViewModel.inputProductColorCode.collectAsState()
    val inputGender by newProductViewModel.inputProductGender.collectAsState()
    val inputStock by newProductViewModel.inputProductStock.collectAsState()
    val inputCode by newProductViewModel.inputProductCode.collectAsState()
    val inputUrlImg by newProductViewModel.inputProductUrlImg.collectAsState()


    LaunchedEffect(Unit) {
        productToEdit?.let {
            newProductViewModel.handleIntent(ProductDetailUiIntent.LoadProduct(it))
        }
    }

    LaunchedEffect(newProductViewModel) {
        newProductViewModel.effectFlow.collectLatest { effect ->
            when (effect) {

                ProductDetailUiEffect.NavigateBack -> {
                    onBackPressed?.invoke()
                }
            }
        }
    }

    NewProductView(
        productToEdit = productToEdit,
        inputName = inputName,
        onInputNameChanged = {
            newProductViewModel.onInputNameChanged(it)
        },
        inputDescription = inputDescription,
        onInputDescriptionChanged = {
            newProductViewModel.onInputDescriptionChanged(it)
        },
        inputBuyPrice = inputBuyPrice,
        onInputBuyPriceChanged = {
            newProductViewModel.onInputBuyPriceChanged(it)
        },
        inputSellPrice = inputSellPrice,
        onInputSellPriceChanged = {
            newProductViewModel.onInputSellPriceChanged(it)
        },
        inputSize = inputSize,
        onInputSizeChanged = {
            newProductViewModel.onInputSizeChanged(it)
        },
        onInputIsNumericSize = isNumericSize,
        onInputIsNumericSizeChanged = {
            newProductViewModel.onInputIsNumericSizeChanged(it)
        },
        inputColor = inputColor,
        onInputColorChanged = {
            newProductViewModel.onInputColorChanged(it)
        },
        inputColorCode = inputColorCode,
        onInputColorCodeChanged = {
            newProductViewModel.onInputColorCodeChanged(it)
        },
        inputGender = inputGender,
        onInputGenderChanged = {
            newProductViewModel.onInputGenderChanged(it)
        },
        inputStock = inputStock,
        onInputStockChanged = {
            newProductViewModel.onInputStockChanged(it)
        },
        inputCode = inputCode,
        onInputCodeChanged = {
            newProductViewModel.onInputCodeChanged(it)
        },
        inputUrlImg = inputUrlImg,
        onUpdateProfilePictureClicked = {
            showProfilePictureDialog = true
        }, onCreateProduct = {
            newProductViewModel.handleIntent(ProductDetailUiIntent.CreateProduct)
        },
        onDeleteProduct = {
            showDialogConfirmDeleteProduct = true
        }, onUpdateProduct = {
            newProductViewModel.handleIntent(ProductDetailUiIntent.UpdateProduct(it))
        }
    )

    // Loading Overlay
    if (uiState.isLoading) {
        Loader(false)
    }

    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsErrorDialog(
            dialogSubTitle = msgError,
            onConfirmation = {
                newProductViewModel.handleIntent(ProductDetailUiIntent.Error)
            })
    }
    uiState.successMessage?.let { msgSuccessOperation ->
        ReportsSuccessDialog(
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                newProductViewModel.handleIntent(ProductDetailUiIntent.HideDialogs)
            })
    }
    if (showProfilePictureDialog) {
        PickerPictureDialog(
            onDismissRequest = { showProfilePictureDialog = false },
            onImageSelected = {
                it?.let {
                    newProductViewModel.handleIntent(ProductDetailUiIntent.SetImageFile(it))
                }
            })
    }

    if (showDialogConfirmDeleteProduct) {
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_vector_remove,
            dialogTitle = stringResource(R.string.msg_delete_main_order_title),
            dialogSubTitle = stringResource(
                R.string.msg_product_confirm_delete
            ),
            confirmButtonText = stringResource(R.string.btn_ok_delete),
            cancelButtonText = stringResource(R.string.btn_cancel),
            onDismissRequest = { showDialogConfirmDeleteProduct = false },
            onConfirmation = {
                showDialogConfirmDeleteProduct = false
                productToEdit?.let {
                    newProductViewModel.handleIntent(ProductDetailUiIntent.DeleteProduct(it))
                }
            }
        )
    }

}


@Preview
@Composable
fun ProductsScreenPreview() {
    ReportsGoTheme {
        NewProductScreen()
    }

}