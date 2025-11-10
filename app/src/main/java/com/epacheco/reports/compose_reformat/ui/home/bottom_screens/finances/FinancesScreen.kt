package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.DateUtils


@OptIn(InternalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen(
    financesViewModel: FinancesViewModel = hiltViewModel<FinancesViewModel>(),
    initialDate: String? = System.currentTimeMillis().toString(),
    finalDate: String? = System.currentTimeMillis().toString(),
    onSelectDateScreen: (() -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
) {

    val uiState by financesViewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
            if(!initialDate.isNullOrEmpty() && !finalDate.isNullOrEmpty()){
                financesViewModel.handleIntent(FinancesUiIntent.SetInitialDate(initialDate.toLong() ))
                financesViewModel.handleIntent(FinancesUiIntent.SetFinalDate(finalDate.toLong() ))

            }
             financesViewModel.handleIntent(FinancesUiIntent.LoadFinancesItems)

        }
    }

    FinancesView(
        orderMainMainList = uiState.financesList,
        isRefreshing = uiState.isLoading,
        onRefresh = {
            financesViewModel.handleIntent(FinancesUiIntent.LoadFinancesItems)
        },
        onBackPressed = {
            onBackPressed?.invoke()
        },
        initialDate = DateUtils.dateFormat(initialDate.toString().ifEmpty { System.currentTimeMillis().toString() }, DateUtils.FORMAT_DATE3),
        finalDate = DateUtils.dateFormat(finalDate.toString().ifEmpty { System.currentTimeMillis().toString() }, DateUtils.FORMAT_DATE3),
        onSelectDatePressed = {
            onSelectDateScreen?.invoke()
        },
    )


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FinancesScreenPreview() {
    ReportsGoTheme {
        FinancesView()
    }

}