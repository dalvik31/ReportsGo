package com.epacheco.reports.compose_reformat.ui.recovery_password

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun PasswordScreen(
    navController: NavController = rememberNavController()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        TextDivider(textDivider = "RecoveryPasswordScreen")
    }
}

@Preview()
@Composable
fun PasswordScreenPreview() {
    ReportsGoTheme {
        PasswordScreen()
    }
}
