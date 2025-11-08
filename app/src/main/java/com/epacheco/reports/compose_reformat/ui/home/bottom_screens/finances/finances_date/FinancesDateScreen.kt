package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.finances.finances_date

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.epacheco.reports.compose_reformat.general_components.SelectorDateDialog
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesDateScreen(
    onDateSelected: ((String, String) -> Unit)? = null,
) {
    Column {
        SelectorDateDialog(
            onDateSelected = { initialDate, finalDate ->
                onDateSelected?.invoke(initialDate.toString(), finalDate.toString())
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductsViewPreview() {
    ReportsGoTheme {
        FinancesDateScreen()
    }
}
