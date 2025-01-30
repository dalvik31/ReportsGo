package com.epacheco.reports.compose_reformat.ui.recovery_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.general_components.HeaderThin
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun RecoveryPassword(registerViewModel: RegisterViewModel?, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        HeaderThin(textHeader = "RecoveryPasswordScreen")
    }
}

@Preview()
@Composable
fun ShowRegisterScreenPreview() {
    ReportsGoTheme {
        RecoveryPassword(null, rememberNavController())
    }
}
