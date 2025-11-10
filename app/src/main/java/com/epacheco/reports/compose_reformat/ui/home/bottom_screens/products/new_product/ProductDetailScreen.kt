package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.products.new_product

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.CheckPermission
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.picture_picker.PickerPictureDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NewProductScreen(
    newProductViewModel: ProductDetailViewModel = hiltViewModel<ProductDetailViewModel>(),
    productToEdit: String? = null,
    onBackPressed: (() -> Unit)? = null,
) {

    val settingResultRequest =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK && activityResult.data != null && activityResult.data?.hasExtra(
                    ScannedBarcodeActivity.CODE_SCANNER
                ) == true
            ) {
                val valueCodeScanned =
                    activityResult.data?.getStringExtra(ScannedBarcodeActivity.CODE_SCANNER) ?: ""
                newProductViewModel.onInputCodeChanged(valueCodeScanned)

            }
        }

    var showScanCodeDialog by remember { mutableStateOf(false) }
    var showProfilePictureDialog by remember { mutableStateOf(false) }
    var showDialogConfirmDeleteProduct by remember { mutableStateOf(false) }

    val uiState by newProductViewModel.uiState.collectAsState()


    val context = LocalContext.current
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
        inputName = uiState.productName,
        onInputNameChanged = {
            newProductViewModel.onInputNameChanged(it)
        },
        inputDescription = uiState.productDescription,
        onInputDescriptionChanged = {
            newProductViewModel.onInputDescriptionChanged(it)
        },
        inputBuyPrice = uiState.productBuyPrice,
        onInputBuyPriceChanged = {
            newProductViewModel.onInputBuyPriceChanged(it)
        },
        inputSellPrice = uiState.productSellPrice,
        onInputSellPriceChanged = {
            newProductViewModel.onInputSellPriceChanged(it)
        },
        inputSize = uiState.productSize,
        onInputSizeChanged = {
            newProductViewModel.onInputSizeChanged(it)
        },
        onInputIsNumericSize = uiState.isProductSizeNumeric,
        onInputIsNumericSizeChanged = {
            newProductViewModel.onInputIsNumericSizeChanged(it)
        },
        inputColor = uiState.productColor,
        onInputColorChanged = {
            newProductViewModel.onInputColorChanged(it)
        },
        inputColorCode = uiState.productColorCode,
        onInputColorCodeChanged = {
            newProductViewModel.onInputColorCodeChanged(it)
        },
        inputGender = uiState.productGender,
        onInputGenderChanged = {
            newProductViewModel.onInputGenderChanged(it)
        },
        inputStock = uiState.productStock,
        onInputStockChanged = {
            newProductViewModel.onInputStockChanged(it)
        },
        inputCode = uiState.productCode,
        onInputCodeChanged = {
            newProductViewModel.onInputCodeChanged(it)
        },
        inputUrlImg = uiState.productUrlImg,
        onOpenScanCodeDialog = {
            showScanCodeDialog = true
        },
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

    if (uiState.isLoading) {
        Loader(false)
    }

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                newProductViewModel.handleIntent(ProductDetailUiIntent.HideDialogs)
            })
    }
    uiState.successMessage?.let { msgSuccessOperation ->
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_ok,
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

    if (showScanCodeDialog) {
        CheckPermission(
            permission = android.Manifest.permission.CAMERA,
            iconPermission = R.drawable.ic_vector_bar_code,
            onGranted = {
                settingResultRequest.launch(
                    Intent(
                        context,
                        ScannedBarcodeActivity::class.java
                    )
                )
                showScanCodeDialog = false

            },
            permissionRationaleTitle = stringResource(R.string.permission_camera_title),
            permissionOpenSettingsTitle = stringResource(R.string.permission_settings_title),
            onCancel = { showScanCodeDialog = false }
        )

    }
    
    if (showDialogConfirmDeleteProduct) {
        ReportsDialog(
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