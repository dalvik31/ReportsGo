package com.epacheco.reports.compose_reformat.domain.user.supabase

interface SignOutUseCase : UseCase<SignOutUseCase.Input, SignOutUseCase.Output> {
    class Input
    sealed class Output {
        data object Success : Output()
        data class Failure(val exception: Exception) : Output()
    }
}