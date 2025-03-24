package com.epacheco.reports.compose_reformat.general_components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.epacheco.reports.R


@Composable
fun ReportsSuccessDialog(
    dialogSubTitle: String,
    closeAutomatically: Boolean? = null,
    onConfirmation: () -> Unit,

    ) {
    ReportsAlertDialog(
        imgDialog = R.drawable.ic_vector_ok,
        dialogTitle = stringResource(R.string.msg_success),
        dialogSubTitle = dialogSubTitle,
        closeAutomatically = closeAutomatically,
        confirmButtonText = if (closeAutomatically == true) null else stringResource(R.string.btn_ok),
        onConfirmation = onConfirmation
    )

}