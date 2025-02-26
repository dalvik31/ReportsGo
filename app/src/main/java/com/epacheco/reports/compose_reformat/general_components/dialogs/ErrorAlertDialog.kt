package com.epacheco.reports.compose_reformat.general_components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epacheco.reports.R

@Composable
fun ErrorAlertDialog(
    dialogTitle: String = stringResource(R.string.msg_error),
    dialogSubTitle: String,
    confirmButtonText: String = stringResource(R.string.btn_ok),
    onConfirmation: () -> Unit,
) {
    GeneralAlertDialog(
        dialogTitle = dialogTitle,
        dialogSubTitle = dialogSubTitle,
        confirmButtonText = confirmButtonText,
        onConfirmation = onConfirmation
    )
}