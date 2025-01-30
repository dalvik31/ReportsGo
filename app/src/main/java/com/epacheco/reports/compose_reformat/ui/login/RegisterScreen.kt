package com.epacheco.reports.compose_reformat.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.general_components.EmailTextField
import com.epacheco.reports.compose_reformat.general_components.Loader
import com.epacheco.reports.compose_reformat.general_components.PasswordTextField
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.SecondaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_HOME
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_PASSWORD
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_REGISTER
import com.epacheco.reports.compose_reformat.ui.theme.FacebookBackground
import com.epacheco.reports.compose_reformat.ui.theme.GoogleBackground
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme
import com.epacheco.reports.compose_reformat.utils.extensions.getTranslateFireBaseErrorMsg

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel?, navController: NavController) {
    val email = registerViewModel?.email?.collectAsState()
    val password = registerViewModel?.password?.collectAsState()
    val enabledButtonContinue = registerViewModel?.enabledLoginButton?.collectAsState()

    val authResource = registerViewModel?.loginFlow?.collectAsState()
    authResource?.value?.let {
        when (it) {
            is Resource.Failure -> {
                if (it.exception != null) {
                    registerViewModel.showToastMsg(it.exception.message?.getTranslateFireBaseErrorMsg())
                    registerViewModel.goToRegisterFlow()
                }
            }

            is Resource.Loading -> Loader()


            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_REGISTER) { inclusive = true }
                    }
                }
            }
        }

    }

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
            Spacer(Modifier.padding(top = 48.dp))
            EmailTextField(email = email?.value ?: "") {
                registerViewModel?.onValueLoginChanged(
                    it,
                    password?.value ?: ""
                )
            }
            Spacer(Modifier.padding(top = 24.dp))
            PasswordTextField(password = password?.value ?: "") {
                registerViewModel?.onValueLoginChanged(email?.value ?: "", it)
            }
            Spacer(Modifier.padding(top = 8.dp))

            Text(
                text = stringResource(id = R.string.register_screen_lbl_forgot_password),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        navController.navigate(ROUTE_PASSWORD)
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
                enabledButton = enabledButtonContinue?.value ?: false
            ) {
                registerViewModel?.loginWithEmail()
            }

            SecondaryButton(
                textButton = stringResource(id = R.string.register_screen_btn_sign_up).uppercase(),
                enabledButton = enabledButtonContinue?.value ?: false
            ) {
                registerViewModel?.createAccount()
            }

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
            Spacer(Modifier.padding(top = 8.dp))
            PrimaryButton(
                textButton = stringResource(id = R.string.register_screen_btn_continue_google),
                iconBtn = R.drawable.ic_vector_google_logo,
                colorBackground = GoogleBackground,
                modifier = Modifier
            ) {

            }
            Spacer(Modifier.padding(top = 48.dp))
        }
    }

}

@Preview()
@Composable
fun ShowRegisterScreenPreview() {
    ReportsGoTheme {
        RegisterScreen(null, rememberNavController())
    }
}