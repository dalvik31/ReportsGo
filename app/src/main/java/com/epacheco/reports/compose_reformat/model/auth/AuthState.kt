package com.epacheco.reports.compose_reformat.model.auth

sealed interface AuthState {
    data object Initializing : AuthState
    data object Authenticated : AuthState
    data object Unauthenticated : AuthState
}