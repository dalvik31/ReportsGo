package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.sales

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.general_components.ListAnimationItem
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsDialog
import com.epacheco.reports.compose_reformat.ui.home.bottom_screens.profile.ProfileUiIntent
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
            if(it.auxStock > 1){
                salesViewModel.handleIntent(
                    SalesUiIntent.UpdateStock(
                        it,
                        false
                    )
                )
            }else{
                Log.e("TAG", "productId: $it.productId")
                productIdToRemoveList = it.productId
            }

        },
        inputClient = uiState.client,
        inputProduct = uiState.product?.productName,
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FinancesScreenPreview() {
    ReportsGoTheme {
        SalesView()
    }

}