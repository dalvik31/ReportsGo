package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.FacebookColor
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    textButton: String,
    iconBtn: Int? = null,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconTint: Color? = null,
    colorBackground: Color = MaterialTheme.colorScheme.primary,
    enabledButton: Boolean = true,
    onButtonClicked: (() -> Unit)? = null
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Button(
            enabled = enabledButton,
            onClick = {
                //we avoid make multiples clicks
                val currentState = lifecycleOwner.lifecycle.currentState
                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    onButtonClicked?.invoke()
                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorBackground,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
            ),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                val filter = if (iconTint != null) ColorFilter.tint(iconTint) else null

                iconBtn?.let { icon ->
                    Image(
                        painterResource(id = icon),
                        contentDescription = "Login google",
                        modifier = Modifier.size(15.dp),
                        colorFilter = filter
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }


                Text(
                    color = textColor,
                    text = textButton.uppercase(),
                    textAlign = TextAlign.Center
                )
            }


        }
    }

}


@Preview
@Composable
private fun GoogleButtonPreview() {
    ReportsGoTheme {
        PrimaryButton(
            textButton = "Icon button",
            iconBtn = R.drawable.ic_vector_google_logo,
            enabledButton = true
        )
    }

}

@Preview
@Composable
private fun FacebookButtonPreview() {
    ReportsGoTheme {
        PrimaryButton(
            textButton = "Icon button",
            iconBtn = R.drawable.ic_vector_facebook_logo,
            colorBackground = FacebookColor,
            enabledButton = true,
        )
    }

}

@Preview()
@Composable
private fun PrimaryButtonPreview() {
    ReportsGoTheme {
        PrimaryButton(
            textButton = "Primary button",
            enabledButton = true,
        )
    }

}
