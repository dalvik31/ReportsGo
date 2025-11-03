package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.new_order

import android.util.Log
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
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.Season
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.clients.detailClient.view.ClientDetailUiIntent
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales.SalesUiIntent
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(InternalCoilApi::class)
@Composable
fun NewOrderScreen(
    newOrderViewModel: NewOrderViewModel = hiltViewModel<NewOrderViewModel>(),
    onNavigateToSelectClient: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    orderToEdit: Order? = null,
    mainOrderId: String?,
    orderSeason: Season?,
    clientIdSelected: String? = null
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

    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {

            clientIdSelected?.let {
                newOrderViewModel.handleIntent(NewOrderUiIntent.GetClientById(clientIdSelected))
            }


            orderToEdit?.let {
                newOrderViewModel.onInputNameChanged(it.orderName)
                newOrderViewModel.onInputStatusChanged(it.orderBuy)
                newOrderViewModel.onInputGenderChanged(it.orderGender)
                newOrderViewModel.onInputColorChanged(it.orderColor)
                newOrderViewModel.onInputSizeChanged(it.orderSize)
                newOrderViewModel.onInputDescriptionChanged(it.orderDescription)
                newOrderViewModel.onInputColorCodeChanged(it.orderColorCode)
                newOrderViewModel.onIsNumericSizeChanged(it.orderSizeNumeric)
                if(clientIdSelected.isNullOrEmpty()){
                    newOrderViewModel.handleIntent(NewOrderUiIntent.GetClientById(it.orderClientId))
                    Log.e("aqui","vamoooos clientIdSelected: ${clientIdSelected}")
                }else{
                    Log.e("aqui","vamoooos2 clientIdSelected: ")
                }


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
        onInputStatus = inputOrderStatus, onInputStatusChanged = {
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
        },
        onInputClientChanged = {
            onNavigateToSelectClient?.invoke()
        })

    //Message error
    uiState.errorMessage?.let { msgError ->
        ReportsDialog(
            imgDialog = R.drawable.ic_error,
            confirmButtonText = stringResource(R.string.btn_ok),
            dialogSubTitle = msgError,
            onConfirmation = {
                newOrderViewModel.handleIntent(NewOrderUiIntent.HideDialogs)
            })
    }

    //Message success
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