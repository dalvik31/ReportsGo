package com.epacheco.reports.compose_reformat.ui.deeplink.state

data class DeepLinkHandlerState(
    val redirectDestination: RedirectDestination = RedirectDestination.Idle
)

sealed interface RedirectDestination {
    data object Idle : RedirectDestination
    data object EmailConfirmation : RedirectDestination
}