package com.epacheco.reports.compose_reformat.general_components.dialogs


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.Black
import com.epacheco.reports.compose_reformat.ui.theme.GreyDark
import com.epacheco.reports.compose_reformat.ui.theme.RedBackground


@Composable
fun ReportsAlertDialog(
    imgDialog: Int,
    dialogTitle: String,
    dialogSubTitle: String,
    confirmButtonText: String,
    cancelButtonText: String? = null,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        CustomDialogUI(
            imgDialog = imgDialog,
            dialogTitle = dialogTitle,
            dialogSubTitle = dialogSubTitle,
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
            Image(
                painter = painterResource(id = imgDialog),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {

                dialogTitle?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .fillMaxWidth(),
                        color = Black,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                dialogSubTitle?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        color = Black,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(RedBackground),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                cancelButtonText?.let {
                    TextButton(onClick = {
                        onDismissRequest?.invoke()
                    }) {

                        Text(
                            it.uppercase(),
                            fontWeight = FontWeight.Bold,
                            color = GreyDark,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
                    }
                }

                confirmButtonText?.let {
                    TextButton(onClick = {
                        onConfirmation?.invoke()
                    }) {
                        Text(
                            it.uppercase(),
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                        )
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