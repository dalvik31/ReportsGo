package com.epacheco.reports.compose_reformat.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.EmailTextField
import com.epacheco.reports.compose_reformat.general_components.PasswordTextField
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.ReportsCheckBox
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.theme.FacebookBackground
import com.epacheco.reports.compose_reformat.ui.theme.GoogleBackground
import com.epacheco.reports.compose_reformat.ui.theme.RedDark
import com.epacheco.reports.compose_reformat.ui.theme.TwitterBackground


@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel) {
    val email: String by registerViewModel.email.observeAsState("")
    val password: String by registerViewModel.password.observeAsState("")
    val enabledButtonContinue: Boolean by registerViewModel.enabledLoginButton.observeAsState(false)
    val checkRememberUser: Boolean by registerViewModel.checkRememberUser.observeAsState(false)

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .weight(1f, true)

        ) {
            Spacer(Modifier.padding(top = 48.dp))
            Text(
                stringResource(id = R.string.register_screen_name_app_logo_first),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 45.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Text(
                stringResource(id = R.string.register_screen_name_app_logo_second),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 45.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            )
            Spacer(Modifier.padding(top = 24.dp))
            EmailTextField(email = email) { registerViewModel.onValueLoginChanged(it, password) }
            Spacer(Modifier.padding(top = 24.dp))
            PasswordTextField(password = password) {
                registerViewModel.onValueLoginChanged(email, it)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ReportsCheckBox(
                    isChecked = checkRememberUser,
                    textCheckBox = stringResource(id = R.string.register_screen_lbl_remember_user)
                ) {
                    registerViewModel.onValueCheckRememberUser(checkRememberUser)
                }
                Text(
                    text = stringResource(id = R.string.register_screen_lbl_forgot_password),
                    modifier = Modifier.weight(1f, fill = false),
                    textAlign = TextAlign.Right,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(Modifier.padding(top = 24.dp))
            PrimaryButton(
                textButton = stringResource(id = R.string.register_screen_btn_login).uppercase(),
                colorBackground = MaterialTheme.colorScheme.primary,
                enabledButton = enabledButtonContinue
            )

            SecondaryButton(
                textButton = stringResource(id = R.string.register_screen_btn_sign_up).uppercase(),
                enabledButton = enabledButtonContinue
            )

            Spacer(Modifier.padding(top = 24.dp))
            TextDivider(stringResource(id = R.string.register_screen_lbl_or))
            Spacer(Modifier.padding(top = 24.dp))
            PrimaryButton(
                textButton = stringResource(id = R.string.register_screen_btn_continue_facebook),
                iconBtn = R.drawable.ic_vector_facebook_logo,
                colorBackground = FacebookBackground,
                modifier = Modifier
            ) {

            }
            PrimaryButton(
                textButton = stringResource(id = R.string.register_screen_btn_continue_google),
                iconBtn = R.drawable.ic_vector_google_logo,
                colorBackground = GoogleBackground,
                modifier = Modifier
            ) {

            }
            PrimaryButton(
                textButton = stringResource(id = R.string.register_screen_btn_continue_twitter),
                iconBtn = R.drawable.ic_vector_twitter_logo,
                colorBackground = TwitterBackground,
                modifier = Modifier
            ) {

            }
            Spacer(Modifier.padding(top = 48.dp))
        }
    }

}

@Preview()
@Composable
private fun ShowRegisterScreenPreview() {
    val viewModel = RegisterViewModel()
    RegisterScreen(viewModel)
}

