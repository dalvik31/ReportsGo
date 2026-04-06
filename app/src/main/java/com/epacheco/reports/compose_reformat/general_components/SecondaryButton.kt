package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme

@Composable
fun SecondaryButton(
    textButton: String,
    modifier: Modifier = Modifier,
    enabledButton: Boolean = true,
    onButtonClicked: (() -> Unit)? = null
) {
    Box(modifier = modifier) {
        TextButton(
            enabled = enabledButton,
            onClick = {
                onButtonClicked?.invoke()
            }
        ) {
            Text(
                text = textButton.uppercase(),
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }
    }

}


@Preview
@Composable
private fun SecondaryButtonPreview() {
    ReportsGoTheme {
        SecondaryButton(textButton = "Action")
    }

}

@Preview
@Composable
private fun DisableSecondaryButtonPreview() {
    ReportsGoTheme {
        SecondaryButton(textButton = "Action", enabledButton = false)
    }

}