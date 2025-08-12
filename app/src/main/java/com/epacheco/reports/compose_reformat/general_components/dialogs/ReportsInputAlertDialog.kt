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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.ui.theme.White


@Composable
fun ReportsInputDialog(
    imgDialog: Int,
    dialogHint: String? = null,
    confirmButtonText: String? = null,
    input: String? = null,
    tintColor: Color = MaterialTheme.colorScheme.primary,
    onConfirmation: () -> Unit,
    onDismissRequest: (() -> Unit),
    onInputChanged: ((String) -> Unit)? = null,
) {
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
        ) {
            Column {

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

                InputTextField(
                    modifier = Modifier
                        .padding(all = 24.dp)
                        .fillMaxWidth(),
                    textValue = input ?: "",
                    textHint = dialogHint ?: "",
                    onTextChange = { onInputChanged?.invoke(it) }
                )
                //.......................................................................
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    confirmButtonText?.let {
                        TextButton(onClick = {
                            onConfirmation.invoke()
                        }) {
                            Text(
                                it.uppercase(),
                                fontWeight = FontWeight.ExtraBold,
                                color = White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 5.dp, bottom = 5.dp)
                            )
                        }
                    }


                }
            }
        }
    }
}

@Preview
@Composable
fun ReportsInputDialogPreview() {
    ReportsGoTheme {
        ReportsInputDialog(
            imgDialog = R.drawable.ic_vector_order,
            dialogHint = "Nombre de la lista",
            onConfirmation = {}, onDismissRequest = {}, confirmButtonText = "Crear lista"
        )
    }

}

