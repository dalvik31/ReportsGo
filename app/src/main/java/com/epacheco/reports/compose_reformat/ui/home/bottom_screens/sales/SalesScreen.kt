package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.OrdersMainUiIntent
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun SalesScreen(
    salesViewModel: SalesViewModel = hiltViewModel<SalesViewModel>(),
    onNavigateToFinances: (() -> Unit)? = null,
    onNavigateToProfile: (() -> Unit)? = null,
    onNavigateToSelectClient: (() -> Unit)? = null,
    onNavigateToSelectProduct: (() -> Unit)? = null,
    productIdSelected: String? = null,
    clientIdSelected: String? = null
) {

    val uiState by salesViewModel.uiState.collectAsState()
    var productIdToRemoveList by remember { mutableStateOf("") }
    var showTypeSaleDialog by remember { mutableStateOf(false) }
    var showNoSelectClientDialog by remember { mutableStateOf(false) }
    var showCreditExceedSaleDialog by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        salesViewModel.handleIntent(SalesUiIntent.GetClientById(clientIdSelected))
        salesViewModel.handleIntent(SalesUiIntent.GetProductById(productIdSelected))
    }

    SalesView(
        onNavigateToFinances = { onNavigateToFinances?.invoke() },
        onNavigateToProfile = { onNavigateToProfile?.invoke() },
        onInputClientChanged = { onNavigateToSelectClient?.invoke() },
        onInputProductChanged = { onNavigateToSelectProduct?.invoke() },
        onIncrementProductToCar = {
            salesViewModel.handleIntent(SalesUiIntent.UpdateStock(it, true))
        },
        onSubtractProductToCar = {
            if (it.auxStock > 1) {
                salesViewModel.handleIntent(
                    SalesUiIntent.UpdateStock(
                        it,
                        false
                    )
                )
            } else {
                Log.e("TAG", "productId: $it.productId")
                productIdToRemoveList = it.productId
            }

        },
        onPayCar = {
            uiState.client?.let {
                showTypeSaleDialog = true
            } ?: run {
                showNoSelectClientDialog = true
            }

        },
        clientSelected = uiState.client,
        productSelected = uiState.product?.productName,
        listProductCart = uiState.cartProducts,
        totalSale = uiState.totalSale
    )

    // Loading Overlay
    if (uiState.isLoading) {
        Loader(false)
    }

    if (productIdToRemoveList.isNotEmpty()) {
        ReportsDialog(
            imgDialog = R.drawable.ic_notfication,
            dialogTitle = stringResource(R.string.title_information),
            dialogSubTitle = stringResource(R.string.remove_product),
            confirmButtonText = stringResource(R.string.btn_ok),
            onConfirmation = {
                salesViewModel.handleIntent(SalesUiIntent.RemoveProductList(productIdToRemoveList))
                productIdToRemoveList = ""

            },
            onDismissRequest = { productIdToRemoveList = "" },
            cancelButtonText = stringResource(R.string.btn_cancel)
        )
    }

    if (showTypeSaleDialog) {
        ReportsDialog(
            imgDialog = R.drawable.ic_notfication,
            dialogTitle = stringResource(R.string.title_information),
            dialogSubTitle = stringResource(R.string.type_transaction),
            confirmButtonText = stringResource(R.string.credit_transaction),
            onConfirmation = {
                salesViewModel.handleIntent(SalesUiIntent.IsCreditSale(true))
                showTypeSaleDialog = false
                val total = uiState.totalSale ?: 0.0
                val limit = uiState.client?.getLimitAvailable() ?: 0.0
                val newLimit = (uiState.client?.limitUsed ?: 0.0) + total
                if (total > limit) {
                    showCreditExceedSaleDialog = true
                } else {
                    salesViewModel.handleIntent(
                        SalesUiIntent.SetNewLimit(
                            uiState.client?.limit ?: 0.0
                        )
                    )
                    salesViewModel.handleIntent(SalesUiIntent.SetNewLimitUsed(newLimit))
                    salesViewModel.handleIntent(SalesUiIntent.SaveSale(true))
                }
            },
            onCancel = {
                salesViewModel.handleIntent(SalesUiIntent.SaveSale())
            },
            onDismissRequest = {
                showTypeSaleDialog = false
            },
            cancelButtonText = stringResource(R.string.cash_transaction)
        )
    }

    if (showNoSelectClientDialog) {
        ReportsDialog(
            imgDialog = R.drawable.ic_notfication,
            dialogTitle = stringResource(R.string.title_information),
            dialogSubTitle = stringResource(R.string.type_not_client_selected),
            confirmButtonText = stringResource(R.string.btn_continue),
            onConfirmation = {
                salesViewModel.handleIntent(SalesUiIntent.SaveSale())
                showNoSelectClientDialog = false
            },
            onDismissRequest = { showNoSelectClientDialog = false },
            cancelButtonText = stringResource(R.string.btn_cancel)
        )
    }

    if (showCreditExceedSaleDialog) {
        val total = uiState.totalSale ?: 0.0
        val limit = uiState.client?.limit ?: 0.0
        val limitAvailable = uiState.client?.getLimitAvailable() ?: 0.0
        val incrementCredit = total - limitAvailable

        ReportsDialog(
            imgDialog = R.drawable.ic_notfication,
            dialogTitle = stringResource(R.string.title_information),
            dialogSubTitle = stringResource(
                R.string.sale_exceeds_credit,
                limit, limit + incrementCredit
            ),
            confirmButtonText = stringResource(R.string.btn_continue),
            onConfirmation = {
                salesViewModel.handleIntent(SalesUiIntent.SetNewLimit(limit +incrementCredit))
                salesViewModel.handleIntent(SalesUiIntent.SetNewLimitUsed(limit +incrementCredit))
                salesViewModel.handleIntent(SalesUiIntent.SaveSale(true))
                showCreditExceedSaleDialog = false
            },
            onDismissRequest = { showCreditExceedSaleDialog = false },
            cancelButtonText = stringResource(R.string.btn_cancel)
        )
    }

    uiState.successOperationMsg?.let { msgSuccessOperation ->
        ReportsDialog(
            imgDialog = R.drawable.ic_vector_ok,
            dialogSubTitle = stringResource(msgSuccessOperation),
            closeAutomatically = true,
            onConfirmation = {
                salesViewModel.handleIntent(SalesUiIntent.HideDialogs)
            })
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FinancesScreenPreview() {
    ReportsGoTheme {
        SalesView()
    }

}