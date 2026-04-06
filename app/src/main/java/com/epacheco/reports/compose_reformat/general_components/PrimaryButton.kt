package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.FacebookColor
import com.epacheco.reports.compose_reformat.ui.theme.GoogleColor
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    textButton: String,
    iconBtn: Int? = null,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconTint: Color = MaterialTheme.colorScheme.onPrimary,
    colorBackground: Color = MaterialTheme.colorScheme.primary,
    enabledButton: Boolean = true,
    onButtonClicked: (() -> Unit)? = null
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    Box(modifier = modifier) {
        Button(
            enabled = enabledButton,
            shape = RoundedCornerShape(8.dp),
            onClick = {
                //we avoid make multiples clicks
                val currentState = lifecycleOwner.lifecycle.currentState
                if (currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                    onButtonClicked?.invoke()
                }

            }, colors = ButtonDefaults.buttonColors(
                containerColor = colorBackground,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
            )
        ) {
            iconBtn?.let { icon ->
                Icon(
                    painterResource(id = icon),
                    contentDescription = "Login google",
                    modifier = Modifier.size(20.dp),
                    tint = iconTint
                )
            }

            Text(
                color = textColor,
                text = textButton.uppercase(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

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
            colorBackground = GoogleColor,
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
            enabledButton = true
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
