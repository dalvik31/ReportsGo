package com.epacheco.reports.compose_reformat.domain.user.supabase.imp

import com.epacheco.reports.compose_reformat.repository.auth.supabase.AuthenticationRepository
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SignUpUseCase {
    override suspend fun execute(input: SignUpUseCase.Input): SignUpUseCase.Output =
        withContext(Dispatchers.IO) {
            authenticationRepository.signUp(input.email, input.password)
        }
}