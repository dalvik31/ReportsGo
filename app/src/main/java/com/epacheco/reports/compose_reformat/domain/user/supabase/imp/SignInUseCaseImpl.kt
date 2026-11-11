package com.epacheco.reports.compose_reformat.domain.user.supabase.imp

import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInUseCase
import com.epacheco.reports.compose_reformat.repository.auth.supabase.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SignInUseCase {
    override suspend fun execute(input: SignInUseCase.Input): SignInUseCase.Output {
        return withContext(Dispatchers.IO) {
            authenticationRepository.signIn(input.email, input.password)
        }
    }
}