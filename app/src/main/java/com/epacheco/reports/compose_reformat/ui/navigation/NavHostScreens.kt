package com.epacheco.reports.compose_reformat.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavHostScreens {

    @Serializable
    data object SplashRoute : NavHostScreens

    @Serializable
    data object RegisterRoute : NavHostScreens

    @Serializable
    data object PasswordRoute : NavHostScreens

    @Serializable
    data object HomeRoute : NavHostScreens

}
