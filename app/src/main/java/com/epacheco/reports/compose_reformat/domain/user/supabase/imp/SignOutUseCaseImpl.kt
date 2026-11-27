package com.epacheco.reports.compose_reformat.domain.user.supabase.imp

import com.epacheco.reports.compose_reformat.domain.user.supabase.SignOutUseCase
import com.epacheco.reports.compose_reformat.repository.auth.supabase.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SignOutUseCase {
    override suspend fun execute(input: SignOutUseCase.Input): SignOutUseCase.Output {
        return withContext(Dispatchers.IO) {
            authenticationRepository.signOut()
        }
    }
}