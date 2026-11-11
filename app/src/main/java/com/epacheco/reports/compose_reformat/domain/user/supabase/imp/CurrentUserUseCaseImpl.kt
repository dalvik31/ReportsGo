package com.epacheco.reports.compose_reformat.domain.user.supabase.imp

import com.epacheco.reports.compose_reformat.domain.user.supabase.CurrentUserUseCase
import com.epacheco.reports.compose_reformat.repository.auth.supabase.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentUserUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : CurrentUserUseCase {
    override suspend fun execute(input: CurrentUserUseCase.Input): CurrentUserUseCase.Output =
        withContext(Dispatchers.IO) {
            authenticationRepository.currentUser()
        }
}