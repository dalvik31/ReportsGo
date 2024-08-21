package com.epacheco.reports.compose_reformat.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_HOME
import com.epacheco.reports.compose_reformat.ui.navigation.ROUTE_LOGIN
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun HomeScreen(registerViewModel: RegisterViewModel?, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextDivider(textDivider = "Home")
        PrimaryButton(textButton = "Logout") {
            registerViewModel?.logout()
            navController.navigate(ROUTE_LOGIN) {
                popUpTo(ROUTE_HOME) { inclusive = true }
            }
        }
    }
}

@Preview
@Composable
fun showHomeScreen() {
    ReportsGoTheme {
        HomeScreen(registerViewModel = null, rememberNavController())
    }

}