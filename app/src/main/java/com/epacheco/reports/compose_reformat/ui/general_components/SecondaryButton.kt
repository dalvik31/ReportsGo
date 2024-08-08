package com.epacheco.reports.compose_reformat.ui.general_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SecondaryButton(
    textButton: String,
    modifier: Modifier = Modifier,
    enabledButton: Boolean = true,
    onButtonClicked: (() -> Unit)? = null
) {
    TextButton(
        enabled = enabledButton,
        onClick = {
            onButtonClicked?.let {
                it.invoke()
            }
        }
    ) {
        Text(
            text = textButton,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}


@Preview
@Composable
private fun showSecondaryButton() {
    SecondaryButton(
        textButton = "Action"
    )
}