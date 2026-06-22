package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(InternalCoilApi::class)
@Composable
fun NewOrderScreen(
    newOrderViewModel: NewOrderViewModel = hiltViewModel<NewOrderViewModel>(),
    onNavigateToSelectClient: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    orderToEdit: String? = null,
    mainOrderId: String?,
    orderSeason: Season?,
    clientIdSelected: String? = null
) {

    val uiState by newOrderViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {

            clientIdSelected?.let {
                newOrderViewModel.handleIntent(NewOrderUiIntent.GetClientById(clientIdSelected))
            }


            orderToEdit?.let {
                newOrderViewModel.handleIntent(
                    NewOrderUiIntent.GetOrderById(
                        mainOrderId ?: "",
                        it,
                        callClientInfo = clientIdSelected.isNullOrEmpty()
                    )
                )
            }

            newOrderViewModel.effectFlow.collectLatest { effect ->
                when (effect) {
                    NewOrderUiEffect.NavigateBack -> {
                        onBackPressed?.invoke()
                    }
                }
            }

        }
    }


    NewOrderView(
        clientSelected = uiState.client,
        onInputStatus = uiState.productStatus, onInputStatusChanged = {
            newOrderViewModel.onInputStatusChanged(it)
        }, onInputIsNumericSize = uiState.isProductSizeNumeric, onInputIsNumericSizeChanged = {
            newOrderViewModel.onIsNumericSizeChanged(it)
        }, inputName = uiState.productName, onInputNameChanged = {
            newOrderViewModel.onInputNameChanged(it)
        },
        inputDescription = uiState.productDescription, onInputDescriptionChanged = {
            newOrderViewModel.onInputDescriptionChanged(it)
        },
        inputSize = uiState.productSize, onInputSizeChanged = {
            newOrderViewModel.onInputSizeChanged(it)
        },
        inputColor = uiState.productColor, onInputColorChanged = {
            newOrderViewModel.onInputColorChanged(it)
        },
        inputColorCode = uiState.productColorCode,
        onInputColorCodeChanged = {
            newOrderViewModel.onInputColorCodeChanged(it)
        },
        orderNameToEdit = uiState.orderToEdit?.orderName,
        inputGender = uiState.productGender, onInputGenderChanged = {
            newOrderViewModel.onInputGenderChanged(it)
        }, onCreateOrder = {
            mainOrderId?.let {
                newOrderViewModel.handleIntent(
                    NewOrderUiIntent.CreateOrder(
                        mainOrderId,
                        orderSeason
                    )
                )
            }
        },
        orderToEdit = orderToEdit,
        onUpdateOrder = {
            orderToEdit?.let {
                newOrderViewModel.handleIntent(NewOrderUiIntent.UpdateOrder)
            }
        }, onDeleteOrder = {
            uiState.orderToEdit?.let { order ->
                newOrderViewModel.handleIntent(
                    NewOrderUiIntent.DeleteOrder(
                        order.orderId,
                        order.orderListId
                    )
                )
            }

        }, onBackPressed = {
            onBackPressed?.invoke()
        },
        onInputClientChanged = {
            onNavigateToSelectClient?.invoke()
        },
        onRemoveClient = {
            newOrderViewModel.handleIntent(NewOrderUiIntent.RemoveClient)
        })

    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                newOrderViewModel.handleIntent(NewOrderUiIntent.HideDialogs)
            })
    }

    uiState.successOperationMsg?.let { msgSuccessOperation ->
        ReportsDialog(
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