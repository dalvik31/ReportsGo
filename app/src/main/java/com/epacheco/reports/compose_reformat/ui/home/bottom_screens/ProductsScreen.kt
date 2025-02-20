package com.epacheco.reports.compose_reformat.ui.home.bottom_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.epacheco.reports.compose_reformat.general_components.PrimaryButton
import com.epacheco.reports.compose_reformat.general_components.TextDivider
import com.epacheco.reports.compose_reformat.ui.login.RegisterViewModel
import com.epacheco.reports.compose_reformat.ui.navigation.NavHostScreens
import com.epacheco.reports.compose_reformat.ui.theme.ReportsGoTheme


@Composable
fun ProductsScreen(
    registerViewModel: RegisterViewModel?,
    navController: NavController,
) {
    Column {
        TextDivider(textDivider = "Products")
        PrimaryButton(textButton = "Logout") {
            registerViewModel?.logout()
            navController.navigate(NavHostScreens.REGISTER.route) {
                popUpTo(NavHostScreens.HOME.route) { inclusive = true }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductsScreenPreview() {
    ReportsGoTheme {
        OrdersScreen(registerViewModel = null, rememberNavController())
    }

}