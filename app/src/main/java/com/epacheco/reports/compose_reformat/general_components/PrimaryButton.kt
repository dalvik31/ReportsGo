package com.epacheco.reports.compose_reformat.general_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.theme.FacebookBackground
import com.epacheco.reports.compose_reformat.ui.theme.GoogleBackground
import com.epacheco.reports.compose_reformat.ui.theme.GreyLight
import com.epacheco.reports.compose_reformat.ui.theme.TwitterBackground
import com.epacheco.reports.compose_reformat.ui.theme.White

@Composable
fun PrimaryButton(
    textButton: String,
    iconBtn: Int? = null,
    colorBackground: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    enabledButton: Boolean = true,
    onButtonClicked: (() -> Unit)? = null
) {
    Button(
        enabled = enabledButton,
        onClick = {
            onButtonClicked?.let {
                it.invoke()
            }
        }, colors = ButtonDefaults.buttonColors(
            containerColor = colorBackground,
            disabledContentColor = White,
            disabledContainerColor = GreyLight,
        )
    ) {
        iconBtn?.let { icon ->
            Icon(
                painterResource(id = icon),
                contentDescription = "Login google",
                modifier = Modifier.size(20.dp),
                tint = White
            )
        }

        Text(
            color = White,
            text = textButton,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}

@Preview
@Composable
private fun showTwitterButton() {
    PrimaryButton(
        textButton = "Twitter",
        iconBtn = R.drawable.ic_vector_twitter_logo,
        colorBackground = TwitterBackground
    )
}

@Preview
@Composable
private fun showGoogleButton() {
    PrimaryButton(
        textButton = "Twitter",
        iconBtn = R.drawable.ic_vector_google_logo,
        colorBackground = GoogleBackground
    )
}

@Preview
@Composable
private fun showFacebookButton() {
    PrimaryButton(
        textButton = "Twitter",
        iconBtn = R.drawable.ic_vector_facebook_logo,
        colorBackground = FacebookBackground
    )
}

@Preview
@Composable
private fun showPrimaryButton() {
    PrimaryButton(
        textButton = "Action",
        colorBackground = MaterialTheme.colorScheme.primary
    )
}

@Preview
@Composable
private fun showSecondaryButton() {
    PrimaryButton(
        textButton = "Action",
        colorBackground = MaterialTheme.colorScheme.primary
    )
}