package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.edit_order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsErrorDialog
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsSuccessDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewOrderScreen(
    editOrderViewModel: EditOrderViewModel = hiltViewModel<EditOrderViewModel>(),
    onBackPressed: (() -> Unit)? = null,
    orderToEdit: Order? = null,
    mainOrderId: String,
    orderSeason: Season?
) {

    val uiState by editOrderViewModel.uiState.collectAsState()
    val inputName by editOrderViewModel.inputProductName.collectAsState()
    val inputDescription by editOrderViewModel.inputProductDescription.collectAsState()
    val inputSize by editOrderViewModel.inputProductSize.collectAsState()
    val inputColor by editOrderViewModel.inputProductColor.collectAsState()
    val inputColorCode by editOrderViewModel.inputProductColorCode.collectAsState()
    val inputGender by editOrderViewModel.inputProductGender.collectAsState()
    val inputOrderStatus by editOrderViewModel.inputProductStatus.collectAsState()
    val isNumericSize by editOrderViewModel.isProductSizeNumeric.collectAsState()

    LaunchedEffect(orderToEdit) {
        orderToEdit?.let {
            editOrderViewModel.onInputNameChanged(it.orderName)
            editOrderViewModel.onInputStatusChanged(it.orderBuy)
            editOrderViewModel.onInputGenderChanged(it.orderGender)
            editOrderViewModel.onInputColorChanged(it.orderColor)
            editOrderViewModel.onInputSizeChanged(it.orderSize)
            editOrderViewModel.onInputDescriptionChanged(it.orderDescription)
            editOrderViewModel.onInputColorCodeChanged(it.orderColorCode)
            editOrderViewModel.onIsNumericSizeChanged(it.orderSizeNumeric)
        }
    }
    LaunchedEffect(editOrderViewModel) {
        editOrderViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                EditOrderUiEffect.NavigateBack -> {
                    onBackPressed?.invoke()
                }
            }
        }
    }

    NewOrderView(onInputStatus = inputOrderStatus, onInputStatusChanged = {
        editOrderViewModel.onInputStatusChanged(it)
    }, onInputIsNumericSize = isNumericSize, onInputIsNumericSizeChanged = {
        editOrderViewModel.onIsNumericSizeChanged(it)
    }, inputName = inputName, onInputNameChanged = {
        editOrderViewModel.onInputNameChanged(it)
    },
        inputDescription = inputDescription, onInputDescriptionChanged = {
            editOrderViewModel.onInputDescriptionChanged(it)
        },
        inputSize = inputSize, onInputSizeChanged = {
            editOrderViewModel.onInputSizeChanged(it)
        },
        inputColor = inputColor, onInputColorChanged = {
            editOrderViewModel.onInputColorChanged(it)
        },
        inputColorCode = inputColorCode,
        onInputColorCodeChanged = {
            editOrderViewModel.onInputColorCodeChanged(it)
        },
        inputGender = inputGender, onInputGenderChanged = {
            editOrderViewModel.onInputGenderChanged(it)
        }, onCreateOrder = {
            editOrderViewModel.handleIntent(EditOrderUiIntent.CreateOrder(mainOrderId, orderSeason))
        },
        orderToEdit = orderToEdit,
        onUpdateOrder = {
            orderToEdit?.let {
                editOrderViewModel.handleIntent(EditOrderUiIntent.UpdateOrder(it))
            }
        }, onDeleteOrder = {
            orderToEdit?.let {
                editOrderViewModel.handleIntent(
                    EditOrderUiIntent.DeleteOrder(
                        it.orderId,
                        it.orderListId
                    )
                )
            }

        }, onBackPressed = {
            onBackPressed?.invoke()
        })

    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsErrorDialog(
            dialogSubTitle = msgError,
            onConfirmation = {
                editOrderViewModel.handleIntent(EditOrderUiIntent.HideDialogs)
            })
    }

    //Message success
    uiState.successOperationMsg?.let { msgSuccessOperation ->
        ReportsSuccessDialog(
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                editOrderViewModel.handleIntent(EditOrderUiIntent.HideDialogs)
            })
    }

}


@Preview
@Composable
fun NewOrderScreenPreview() {
    ReportsGoTheme {
        NewOrderView()
    }
}