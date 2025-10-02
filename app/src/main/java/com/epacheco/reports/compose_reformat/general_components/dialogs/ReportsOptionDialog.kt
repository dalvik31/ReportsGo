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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import kotlinx.coroutines.delay


@Composable
fun ReportsOptionDialog(
    imgDialog: Int,
    dialogTitle: String? = null,
    dialogSubTitle: String? = null,
    firstOptionText: String? = null,
    secondOptionText: String? = null,
    closeAutomatically: Boolean? = null,
    onDismissRequest: (() -> Unit)? = null,
    onFirstConfirmation: () -> Unit,
    onSecondConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        CustomOptionDialogUI(
            imgDialog = imgDialog,
            dialogTitle = dialogTitle,
            dialogSubTitle = dialogSubTitle,
            closeAutomatically = closeAutomatically,
            firstOptionText = firstOptionText,
            secondOptionText = secondOptionText,
            onDismissRequest = onDismissRequest,
            onFirstConfirmation = onFirstConfirmation,
            onSecondConfirmation = onSecondConfirmation
        )
    }
}

//Layout
@Composable
fun CustomOptionDialogUI(
    modifier: Modifier = Modifier,
    imgDialog: Int = R.drawable.ic_notfication,
    dialogTitle: String? = null,
    dialogSubTitle: String? = null,
    firstOptionText: String? = null,
    secondOptionText: String? = null,
    closeAutomatically: Boolean? = null,
    onDismissRequest: (() -> Unit)? = null,
    onFirstConfirmation: (() -> Unit)? = null,
    onSecondConfirmation: (() -> Unit)? = null,
) {


    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier
                .background(Color.Unspecified)
                .padding(horizontal = 30.dp)
                .padding(top = 30.dp, bottom = 20.dp)
        ) {

            //.......................................................................
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

            Column {

                dialogTitle?.let {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                dialogSubTitle?.let {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Text(
                        text = AnnotatedString.fromHtml(it),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                }
                closeAutomatically?.let {
                    LaunchedEffect(it) {
                        delay(2000)  // the delay of 3 seconds
                        onDismissRequest?.invoke()
                    }
                } ?: run {

                    firstOptionText?.let {
                        PrimaryButton(textButton = it.uppercase(), onButtonClicked = {
                            onFirstConfirmation?.invoke()
                        })

                    }
                    secondOptionText?.let {
                        SecondaryButton(textButton = it.uppercase(), onButtonClicked = {
                            onSecondConfirmation?.invoke()
                        })
                    }

                }

            }

        }
    }
}


@Preview
@Composable
fun ReportsOptionPreview() {
    ReportsOptionDialog(
        R.drawable.ic_vector_order,
        dialogTitle = "Opciones",
        dialogSubTitle = "¿Que accion deseas realizar?",
        firstOptionText = "Marcar como completado",
        secondOptionText = "Eliminar",
        onFirstConfirmation = {},
        onSecondConfirmation = {}
    )
}
