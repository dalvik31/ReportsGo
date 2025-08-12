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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.White

@Composable
fun ReportsInfoDialog(
    imgDialog: Int? = null,
    lottieAnimation: Int? = null,
    background: Color? = null,
    dialogTitle: String? = null,
    dialogSubTitle: String? = null,
    confirmButtonText: String? = null,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: (() -> Unit)? = null,
) {

    var lottieComposition: LottieComposition? = null
    var lottieProgress: Float? = null

    lottieAnimation?.let { lottieRaw ->
        val preloaderLottieComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(lottieRaw)
        )
        val preloaderProgress by animateLottieCompositionAsState(
            preloaderLottieComposition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true
        )
        lottieComposition = preloaderLottieComposition
        lottieProgress = preloaderProgress
    }

    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
        ) {
            Column {

                lottieComposition?.let {
                    LottieAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RectangleShape)
                            .background(color = background ?: Color.Unspecified),
                        composition = it,
                        progress = lottieProgress ?: 0f,
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    imgDialog?.let {
                        val painter = painterResource(id = it)
                        Image(
                            painter = painter,
                            contentDescription = null, // decorative
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(color = background ?: Color.Unspecified),
                        )
                    }
                }

                Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)) {

                    dialogTitle?.let {
                        Text(
                            text = it,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    dialogSubTitle?.let {
                        Text(
                            text = AnnotatedString.fromHtml(it),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    confirmButtonText?.let {
                        TextButton(onClick = {
                            onConfirmation?.invoke()
                        }) {
                            Text(
                                it.uppercase(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.ExtraBold,
                                color = White,
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
fun ReportsInfoDialogPreview() {
    ReportsInfoDialog(
        dialogTitle = "Informacion",
        dialogSubTitle = "Ocurrio un error intenta mas tarde",
        background = Color.Black,
        onConfirmation = {},
        onDismissRequest = {},
        imgDialog = R.drawable.ic_vector_sale_emmpty,
        lottieAnimation = R.raw.fall,
        confirmButtonText = "Aceptar"
    )
}
