package com.epacheco.reports.compose_reformat.ui.account

import android.graphics.ColorFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.general_components.Header
import com.epacheco.reports.compose_reformat.general_components.InputTextField
import com.epacheco.reports.compose_reformat.general_components.PasswordTextField
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.theme.FacebookColor
import com.epacheco.reports.compose_reformat.ui.theme.GoogleColor
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun AccountView(
    email: String? = null,
    password: String? = null,
    enabledButtonContinue: Boolean? = null,
    onEmailChanged: ((String, String) -> Unit)? = null,
    onPasswordChanged: ((String, String) -> Unit)? = null,
    onLoginClicked: (() -> Unit)? = null,
    onLoginGoogleClicked: (() -> Unit)? = null,
    onPasswordClicked: (() -> Unit)? = null,
    onRegisterClicked: (() -> Unit)? = null

) {


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(horizontal = 24.dp),

    ) {
        Text(
            text = "ReportsGO",
            modifier = Modifier
                .padding(vertical = 60.dp)
                .padding(horizontal = 24.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        /*Box(modifier = Modifier.align(Alignment.CenterHorizontally), contentAlignment = Alignment.Center,){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    ,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                painter = painterResource(R.drawable.logo_reports_go),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(text = "Reports Go", color = MaterialTheme.colorScheme.onPrimary,fontWeight = FontWeight.Light,)
        }*/

        InputTextField(
            textHint = stringResource(R.string.register_screen_hint_email),
            textValue = email ?: "",
            onTextChange = { onEmailChanged?.invoke(it, password ?: "") },
            keyboardType = KeyboardType.Email,
        )
        Spacer(Modifier.padding(top = 24.dp))

        PasswordTextField(
            password = password ?: "",
            passwordHint = stringResource(R.string.register_screen_hint_password)
        ) {
            onPasswordChanged?.invoke(email ?: "", it)

        }
        Spacer(Modifier.padding(top = 8.dp))

        Text(
            text = stringResource(id = R.string.register_screen_lbl_forgot_password),
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onPasswordClicked?.invoke()
                    //navController?.navigate(NavHostScreens.PASSWORD.route)
                },
            textAlign = TextAlign.Right,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.padding(top = 24.dp))
        PrimaryButton(
            textButton = stringResource(id = R.string.register_screen_btn_login).uppercase(),
            colorBackground = MaterialTheme.colorScheme.primary,
            enabledButton = enabledButtonContinue ?: false
        ) {
            onLoginClicked?.invoke()

        }

        SecondaryButton(
            textButton = stringResource(id = R.string.register_screen_btn_sign_up).uppercase(),
            enabledButton = enabledButtonContinue ?: false
        ) {
            onRegisterClicked?.invoke()

        }

        Spacer(Modifier.padding(top = 24.dp))
        TextDivider(textDivider = stringResource(id = R.string.register_screen_lbl_or))
        Spacer(Modifier.padding(top = 24.dp))
        /* PrimaryButton(
            textButton = stringResource(id = R.string.register_screen_btn_continue_facebook),
            iconBtn = R.drawable.ic_vector_facebook_logo,
            colorBackground = FacebookColor,
            modifier = Modifier
        ) {

        }*/
        Spacer(Modifier.padding(top = 8.dp))
        PrimaryButton(
            textButton = stringResource(id = R.string.register_screen_btn_continue_google),
            iconBtn = R.drawable.ic_vector_google_logo,
            colorBackground = GoogleColor,
            modifier = Modifier
        ) {
            onLoginGoogleClicked?.invoke()
        }
        Spacer(Modifier.padding(top = 48.dp))
    }

}

@Preview(showSystemUi = true)
@Composable
fun ShowRegisterScreenPreview() {
    ReportsGoTheme {
        AccountView()
    }
}