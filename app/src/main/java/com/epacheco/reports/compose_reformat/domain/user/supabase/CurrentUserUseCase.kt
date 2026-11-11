package com.epacheco.reports.compose_reformat.domain.user.supabase

import com.epacheco.reports.compose_reformat.model.user.UserInfoProfile

interface CurrentUserUseCase: UseCase<CurrentUserUseCase.Input, CurrentUserUseCase.Output> {
    class Input
    sealed class Output {
        data class Success(val userInfo: UserInfoProfile) : Output()
        data class Failure(val exception: Exception) : Output()
    }
}