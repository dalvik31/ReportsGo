package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorDateDialog(
    modifier: Modifier = Modifier,
    initialDate: Long? = System.currentTimeMillis(),
    finalDate: Long? = System.currentTimeMillis(),
    onDateSelected: ((Long, Long) -> Unit)? = null,
) {
    val state =
        rememberDateRangePickerState(
            initialSelectedStartDateMillis = initialDate,
            initialSelectedEndDateMillis = finalDate
        )

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column {
            DateRangePicker(
                state = state,
                modifier = modifier
                    .weight(1f)
            )
        }

        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .padding(bottom = 24.dp),
            textButton = stringResource(
                R.string.msg_date_selected,
            )
        ) {
            val startDate = state.selectedStartDateMillis ?: System.currentTimeMillis()
            val endDate = state.selectedEndDateMillis ?: startDate
            onDateSelected?.invoke(
                startDate,
                endDate
            )
        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SelectorDateDialogPreview() {
    ReportsGoTheme {
        SelectorDateDialog()
    }
}