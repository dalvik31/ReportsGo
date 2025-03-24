package com.epacheco.reports.compose_reformat.general_components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.epacheco.reports.R


@Composable
fun ReportsErrorDialog(
    dialogSubTitle: String,
    onConfirmation: () -> Unit,
) {
    ReportsAlertDialog(
        imgDialog = R.drawable.ic_error,
        dialogTitle = stringResource(R.string.msg_error),
        dialogSubTitle = dialogSubTitle,
        confirmButtonText = stringResource(R.string.btn_ok),
        onConfirmation = onConfirmation
    )
}