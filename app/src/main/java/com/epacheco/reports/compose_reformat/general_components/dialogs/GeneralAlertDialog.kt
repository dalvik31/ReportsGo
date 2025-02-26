package com.epacheco.reports.compose_reformat.general_components.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@Composable
fun GeneralAlertDialog(
    dialogTitle: String? = null,
    dialogSubTitle: String? = null,
    confirmButtonText: String? = null,
    cancelButtonText: String? = null,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: (() -> Unit)? = null,
) {

    AlertDialog(
        modifier = Modifier.fillMaxWidth(0.92f),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        containerColor = Color.DarkGray,
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        confirmButton = {
            confirmButtonText?.let {
                TextButton(onClick = { onConfirmation?.invoke() }) {
                    Text(text = it, color = Color.White)
                }
            }
        },
        dismissButton = {
            cancelButtonText?.let {
                TextButton(onClick = { onDismissRequest?.invoke() }) {
                    Text(text = it, color = Color.White)
                }
            }

        },
        title = {
            dialogTitle?.let {
                Text(text = it, fontSize = 18.sp, color = Color.White)
            }
        },
        text = {
            dialogSubTitle?.let {
                Text(text = it, color = Color.White)
            }

        })
}


@Preview
@Composable

fun GeneralAlertDialogPreview() {
    GeneralAlertDialog(
        dialogTitle = "Error",
        dialogSubTitle = "dialogSubTitle",
        confirmButtonText = "Aceptar"
    )
}