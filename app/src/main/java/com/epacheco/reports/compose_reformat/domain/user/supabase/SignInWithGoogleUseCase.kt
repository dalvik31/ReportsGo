package com.epacheco.reports.compose_reformat.domain.user.supabase

import io.github.jan.supabase.auth.user.UserInfo

interface SignInWithGoogleUseCase :
    UseCase<SignInWithGoogleUseCase.Input, SignInWithGoogleUseCase.Output> {
    class Input
    sealed class Output {
        data object Success : Output()
        data class Failure(val exception: Exception) : Output()
    }
}