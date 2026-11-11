package com.epacheco.reports.compose_reformat.domain.user.supabase

import com.epacheco.reports.compose_reformat.model.user.UserInfoProfile

interface SignUpUseCase: UseCase<SignUpUseCase.Input, SignUpUseCase.Output> {
    class Input(val email: String, val password: String)
    sealed class Output {
        data class Success(val userInfo: UserInfoProfile) : Output()
        data class Failure(val exception: Exception) : Output()
    }
}