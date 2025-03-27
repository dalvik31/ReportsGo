package com.epacheco.reports.compose_reformat.ui.home.bottom_screens.orders.main_orders_detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.dialogs.ReportsInputDialog


@Composable
fun OrderInputDialog(
    onInputChanged: ((String) -> Unit)? = null,
    input: String? = null,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    ReportsInputDialog(
        imgDialog = R.drawable.shopping_bag,
        dialogTitle = "Pedido",
        confirmButtonText = stringResource(R.string.btn_ok),
        dialogHint = "Mi nuevo pedido",
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
    OrderInputDialog(
        onConfirmation = {},
        onDismissRequest = {},
        tintColor = MaterialTheme.colorScheme.primary
    )
}