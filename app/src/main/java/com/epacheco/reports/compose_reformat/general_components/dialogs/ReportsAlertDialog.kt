package com.epacheco.reports.compose_reformat.general_components.dialogs


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.Dialog
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import com.epacheco.reports.compose_reformat.ui.theme.Black
import com.epacheco.reports.compose_reformat.ui.theme.GrayDark
import com.epacheco.reports.compose_reformat.ui.theme.RedBackground
import kotlinx.coroutines.delay


@Composable
fun ReportsAlertDialog(
    imgDialog: Int,
    dialogTitle: String? = null,
    dialogSubTitle: String? = null,
    confirmButtonText: String? = null,
    cancelButtonText: String? = null,
    closeAutomatically: Boolean? = null,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation:  () -> Unit,
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
            onConfirmation = onConfirmation
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
) {


    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {

            //.......................................................................
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Image(
                painter = painterResource(id = imgDialog),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 30.dp)) {

                dialogTitle?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Black,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                dialogSubTitle?.let {
                    Text(
                        text = AnnotatedString.fromHtml(it),
                        textAlign = TextAlign.Center,
                        color = Black,
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                closeAutomatically?.let {
                    LaunchedEffect(it) {
                        delay(2000)  // the delay of 3 seconds
                        onConfirmation?.invoke()
                    }
                } ?: run {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    confirmButtonText?.let {
                        PrimaryButton(textButton = it, onButtonClicked = {
                            onConfirmation?.invoke()
                        })
                    }

                    cancelButtonText?.let {
                        SecondaryButton(textButton = it, onButtonClicked = {
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
    ReportsErrorDialog(
        dialogSubTitle = "Ocurrio un error intenta mas tarde",
    ) {}
}

@Preview
@Composable
fun ReportsSuccessDialogPreview() {
    ReportsSuccessDialog(
        dialogSubTitle = "Ocurrio un error intenta mas tarde",
    ) {}
}