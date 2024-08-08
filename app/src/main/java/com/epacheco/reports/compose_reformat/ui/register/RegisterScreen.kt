package com.epacheco.reports.compose_reformat.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ui.general_components.EmailTextField
import com.epacheco.reports.compose_reformat.ui.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.ui.general_components.SecondaryButton
import com.epacheco.reports.compose_reformat.ui.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.general_components.otherEmail
import com.epacheco.reports.compose_reformat.ui.theme.FacebookBackground
import com.epacheco.reports.compose_reformat.ui.theme.GoogleBackground
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.TwitterBackground


@Composable
fun RegisterScreen() {

    Column(verticalArrangement = Arrangement.SpaceBetween) {


        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .weight(1f, true)

        ) {
            Spacer(Modifier.padding(top = 24.dp))
            Text(
                "Reports",
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                color = RedDark,
                fontSize = 45.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Text(
                "GO",
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                color = RedDark,
                fontSize = 45.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Spacer(Modifier.padding(top = 24.dp))

            TextField(
                value = "email",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Email") },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color(0xFFB2B2B2),
                    unfocusedContainerColor = Color(0xFFFAFAFA),
                    focusedContainerColor = Color(0xFFFAFAFA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.padding(top = 24.dp))
            otherEmail()
            Spacer(Modifier.padding(top = 24.dp))
            PrimaryButton(
                textButton = "INICIA SESION",
                colorBackground = MaterialTheme.colorScheme.primary
            )

            SecondaryButton(
                textButton = "REGISTRATE"
            )

            Spacer(Modifier.padding(top = 24.dp))
            TextDivider("Ó")
            Spacer(Modifier.padding(top = 24.dp))
            PrimaryButton(
                textButton = "Continua con facebook",
                iconBtn = R.drawable.ic_vector_facebook_logo,
                colorBackground = FacebookBackground,
                modifier = Modifier
            ) {

            }
            PrimaryButton(
                textButton = "Continua con google",
                iconBtn = R.drawable.ic_vector_google_logo,
                colorBackground = GoogleBackground,
                modifier = Modifier
            ) {

            }
            PrimaryButton(
                textButton = "Continua con twitter",
                iconBtn = R.drawable.ic_vector_twitter_logo,
                colorBackground = TwitterBackground,
                modifier = Modifier
            ) {

            }


        }
    }

}

@Preview
@Composable
private fun showRegisterScreenPreview() {
    RegisterScreen()
}

