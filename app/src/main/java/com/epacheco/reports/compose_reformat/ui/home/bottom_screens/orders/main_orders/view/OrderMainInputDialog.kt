package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsInputDialog


@Composable
fun OrderMainInputDialog(
    onInputChanged: ((String) -> Unit)? = null,
    input: String? = null,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    ReportsInputDialog(
        imgDialog = R.drawable.ic_vector_order,
        confirmButtonText = stringResource(R.string.btn_create_order_list),
        dialogHint = stringResource(R.string.title_create_order_list),
        input = input,
        tintColor = tintColor,
        onConfirmation = onConfirmation,
        onDismissRequest = onDismissRequest,
        onInputChanged = onInputChanged
    )

}


@Preview
@Composable
fun ReportsInputAlertDialogPreview() {
    OrderMainInputDialog(
        onConfirmation = {},
        onDismissRequest = {},
        tintColor = MaterialTheme.colorScheme.primary
    )
}