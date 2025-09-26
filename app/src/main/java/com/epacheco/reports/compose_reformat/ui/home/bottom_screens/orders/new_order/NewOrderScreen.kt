package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsAlertDialog
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewOrderScreen(
    newOrderViewModel: NewOrderViewModel = hiltViewModel<NewOrderViewModel>(),
    onBackPressed: (() -> Unit)? = null,
    orderToEdit: Order? = null,
    mainOrderId: String,
    orderSeason: Season?
) {

    val uiState by newOrderViewModel.uiState.collectAsState()
    val inputName by newOrderViewModel.inputProductName.collectAsState()
    val inputDescription by newOrderViewModel.inputProductDescription.collectAsState()
    val inputSize by newOrderViewModel.inputProductSize.collectAsState()
    val inputColor by newOrderViewModel.inputProductColor.collectAsState()
    val inputColorCode by newOrderViewModel.inputProductColorCode.collectAsState()
    val inputGender by newOrderViewModel.inputProductGender.collectAsState()
    val inputOrderStatus by newOrderViewModel.inputProductStatus.collectAsState()
    val isNumericSize by newOrderViewModel.isProductSizeNumeric.collectAsState()

    LaunchedEffect(orderToEdit) {
        orderToEdit?.let {
            newOrderViewModel.onInputNameChanged(it.orderName)
            newOrderViewModel.onInputStatusChanged(it.orderBuy)
            newOrderViewModel.onInputGenderChanged(it.orderGender)
            newOrderViewModel.onInputColorChanged(it.orderColor)
            newOrderViewModel.onInputSizeChanged(it.orderSize)
            newOrderViewModel.onInputDescriptionChanged(it.orderDescription)
            newOrderViewModel.onInputColorCodeChanged(it.orderColorCode)
            newOrderViewModel.onIsNumericSizeChanged(it.orderSizeNumeric)
        }
    }
    LaunchedEffect(newOrderViewModel) {
        newOrderViewModel.effectFlow.collectLatest { effect ->
            when (effect) {
                NewOrderUiEffect.NavigateBack -> {
                    onBackPressed?.invoke()
                }
            }
        }
    }

    NewOrderView(onInputStatus = inputOrderStatus, onInputStatusChanged = {
        newOrderViewModel.onInputStatusChanged(it)
    }, onInputIsNumericSize = isNumericSize, onInputIsNumericSizeChanged = {
        newOrderViewModel.onIsNumericSizeChanged(it)
    }, inputName = inputName, onInputNameChanged = {
        newOrderViewModel.onInputNameChanged(it)
    },
        inputDescription = inputDescription, onInputDescriptionChanged = {
            newOrderViewModel.onInputDescriptionChanged(it)
        },
        inputSize = inputSize, onInputSizeChanged = {
            newOrderViewModel.onInputSizeChanged(it)
        },
        inputColor = inputColor, onInputColorChanged = {
            newOrderViewModel.onInputColorChanged(it)
        },
        inputColorCode = inputColorCode,
        onInputColorCodeChanged = {
            newOrderViewModel.onInputColorCodeChanged(it)
        },
        inputGender = inputGender, onInputGenderChanged = {
            newOrderViewModel.onInputGenderChanged(it)
        }, onCreateOrder = {
            newOrderViewModel.handleIntent(NewOrderUiIntent.CreateOrder(mainOrderId, orderSeason))
        },
        orderToEdit = orderToEdit,
        onUpdateOrder = {
            orderToEdit?.let {
                newOrderViewModel.handleIntent(NewOrderUiIntent.UpdateOrder(it))
            }
        }, onDeleteOrder = {
            orderToEdit?.let {
                newOrderViewModel.handleIntent(
                    NewOrderUiIntent.DeleteOrder(
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
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                newOrderViewModel.handleIntent(NewOrderUiIntent.HideDialogs)
            })
    }

    //Message success
    uiState.successOperationMsg?.let { msgSuccessOperation ->
        ReportsAlertDialog(
            imgDialog = R.drawable.ic_vector_ok,
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                newOrderViewModel.handleIntent(NewOrderUiIntent.HideDialogs)
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