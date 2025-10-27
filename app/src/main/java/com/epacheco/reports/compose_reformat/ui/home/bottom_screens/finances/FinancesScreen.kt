package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil3.annotation.InternalCoilApi
import coil3.request.GlobalLifecycle.currentState
import com.epacheco.reports.compose_reformat.general_components.SelectorDateDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.DateUtils


@OptIn(InternalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen(
    financesViewModel: FinancesViewModel = hiltViewModel<FinancesViewModel>(),
    onBackPressed: (() -> Unit)? = null,
) {

    var showSelectorDateDialog by remember { mutableStateOf(false) }
    val uiState by financesViewModel.uiState.collectAsState()


    LaunchedEffect(Unit) {
        if (currentState.isAtLeast(Lifecycle.State.STARTED)) {
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
        initialDate = DateUtils.dateFormat(uiState.initialDate.toString(), DateUtils.FORMAT_DATE1),
        finalDate = DateUtils.dateFormat(uiState.finalDate.toString(),DateUtils.FORMAT_DATE1),
        onSelectDatePressed = {
            showSelectorDateDialog = true
        },
    )

    if (showSelectorDateDialog) {
        SelectorDateDialog(
            onDateSelected = { initialDate, finalDate ->
                showSelectorDateDialog = false
                financesViewModel.handleIntent(FinancesUiIntent.SetInitialDate(initialDate))
                financesViewModel.handleIntent(FinancesUiIntent.SetFinalDate(finalDate))
                financesViewModel.handleIntent(FinancesUiIntent.LoadFinancesItems)
            },
            onDismiss = {
                showSelectorDateDialog = false
            }
        )
    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FinancesScreenPreview() {
    ReportsGoTheme {
        FinancesView()
    }

}