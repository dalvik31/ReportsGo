package com.epacheco.reports.compose_reformat.ui.navigation

sealed class NavHostScreens(val route: String) {
    data object REGISTER : NavHostScreens("register_screen")
    data object PASSWORD : NavHostScreens("password_screen")
    data object HOME : NavHostScreens("home_screen")
}
