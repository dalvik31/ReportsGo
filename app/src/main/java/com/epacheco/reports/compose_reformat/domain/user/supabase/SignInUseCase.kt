package com.epacheco.reports.compose_reformat.domain.user.supabase

import com.epacheco.reports.compose_reformat.model.user.UserInfoProfile


interface SignInUseCase : UseCase<SignInUseCase.Input, SignInUseCase.Output> {
    class Input(val email: String, val password: String)
    sealed class Output {
        data class Success(val userInfo: UserInfoProfile) : Output()
        data class Failure(val exception: Exception) : Output()
    }
}