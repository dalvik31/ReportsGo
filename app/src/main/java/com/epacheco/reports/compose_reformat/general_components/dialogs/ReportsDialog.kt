package com.epacheco.reports.compose_reformat.general_components.dialogs


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import kotlinx.coroutines.delay


@Composable
fun ReportsDialog(
    imgDialog: Int,
    dialogTitle: String? = null,
    dialogSubTitle: String? = null,
    confirmButtonText: String? = null,
    cancelButtonText: String? = null,
    closeAutomatically: Boolean? = null,
    onDismissRequest: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        CustomDialogUI(
            imgDialog = imgDialog,
            dialogTitle = dialogTitle,
            dialogSubTitle = dialogSubTitle,
            closeAutomatically = closeAutomatically,
            confirmButtonText = confirmButtonText,
            cancelButtonText = cancelButtonText,
            onDismissRequest = onDismissRequest,
            onConfirmation = onConfirmation,
            onCancel = onCancel
        )
    }
}

//Layout
@Composable
fun CustomDialogUI(
    modifier: Modifier = Modifier,
    imgDialog: Int = R.drawable.ic_notfication,
    dialogTitle: String? = null,
    dialogSubTitle: String? = null,
    confirmButtonText: String? = null,
    cancelButtonText: String? = null,
    closeAutomatically: Boolean? = null,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
) {


    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier
                .background(Color.Unspecified)
                .padding(horizontal = 30.dp)
                .padding(top = 30.dp, bottom = 20.dp)
        ) {
            Image(
                painter = painterResource(id = imgDialog),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),

                )

            Column {

                dialogTitle?.let {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                dialogSubTitle?.let {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(
                        text = AnnotatedString.fromHtml(it),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                }
                closeAutomatically?.let {
                    LaunchedEffect(it) {
                        delay(2000)  // the delay of 3 seconds
                        onConfirmation?.invoke()
                    }
                } ?: run {

                    confirmButtonText?.let {
                        PrimaryButton(textButton = it.uppercase(), onButtonClicked = {
                            onConfirmation?.invoke()
                        })

                    }
                    cancelButtonText?.let {
                        SecondaryButton(textButton = it.uppercase(), onButtonClicked = {
                            onCancel?.invoke()
                            onDismissRequest?.invoke()
                        })
                    }

                }

            }

        }
    }
}


@Preview
@Composable
fun ReportsErrorDialogPreview() {
    ReportsDialog(
        R.drawable.ic_error,
        dialogSubTitle = "Ocurrio un error intenta mas tarde",
        confirmButtonText = "Aceptar",
        cancelButtonText = "Cancel"
    ) {}
}

@Preview
@Composable
fun ReportsSuccessDialogPreview() {
    ReportsDialog(
        R.drawable.ic_vector_ok,
        dialogTitle = "Info",
        dialogSubTitle = "La operacion se ejecuto con exito",
        confirmButtonText = "Aceptar",
    ) {}
}

@Preview
@Composable
fun ReportsSuccessCancelDialogPreview() {
    ReportsDialog(
        R.drawable.ic_vector_ok,
        dialogSubTitle = "La operacion se ejecuto con exito",
        cancelButtonText = "Cancelar",
    ) {}
}

@Preview
@Composable
fun ReportsSuccessAutomaticallyDialogPreview() {
    ReportsDialog(
        R.drawable.ic_vector_ok,
        dialogSubTitle = "La operacion se ejecuto con exito",
        closeAutomatically = true,
        confirmButtonText = "Aceptar",
        cancelButtonText = "Cancel"
    ) {}
}