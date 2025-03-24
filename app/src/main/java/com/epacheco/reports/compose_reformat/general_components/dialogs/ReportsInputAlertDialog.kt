package com.epacheco.reports.compose_reformat.general_components.dialogs


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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.ui.theme.Black
import com.epacheco.reports.compose_reformat.ui.theme.White


@Composable
fun ReportsInputDialog(
    imgDialog: Int,
    dialogTitle: String,
    dialogHint: String? = null,
    confirmButtonText: String? = null,
    input: String? = null,
    tintColor: Color,
    onConfirmation: () -> Unit,
    onDismissRequest: (() -> Unit)? = null,
    onInputChanged: ((String) -> Unit)? = null,
) {
    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        CustomInputDialogUI(
            imgDialog = imgDialog,
            dialogTitle = dialogTitle,
            dialogHint = dialogHint,
            input = input,
            tintColor = tintColor,
            confirmButtonText = confirmButtonText,
            onConfirmation = onConfirmation,
            onInputChanged = onInputChanged,

            )
    }

}

//Layout
@Composable
fun CustomInputDialogUI(
    modifier: Modifier = Modifier,
    imgDialog: Int = R.drawable.ic_notfication,
    dialogTitle: String? = null,
    dialogHint: String? = null,
    tintColor: Color,
    input: String? = null,
    confirmButtonText: String? = null,
    onConfirmation: (() -> Unit)? = null,
    onInputChanged: ((String) -> Unit)? = null,

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
                    color = tintColor
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(vertical = 16.dp)) {

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

                InputTextField(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    input = input ?: "",
                    tintColor = tintColor,
                    hintText = dialogHint ?: ""
                ) {
                    onInputChanged?.invoke(it)

                }


            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(tintColor),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                confirmButtonText?.let {
                    TextButton(onClick = {
                        onConfirmation?.invoke()
                    }) {
                        Text(
                            it.uppercase(),
                            fontWeight = FontWeight.ExtraBold,
                            color = White,
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
fun ReportsInputDialogPreview() {
    OrderInputDialog(onConfirmation = {}, onDismissRequest = {})
}

