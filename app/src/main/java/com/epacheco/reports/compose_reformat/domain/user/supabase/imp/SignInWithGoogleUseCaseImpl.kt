package com.epacheco.reports.compose_reformat.domain.user.supabase.imp

import com.epacheco.reports.compose_reformat.repository.auth.supabase.AuthenticationRepository
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInWithGoogleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInWithGoogleUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : SignInWithGoogleUseCase {
    override suspend fun execute(input: SignInWithGoogleUseCase.Input): SignInWithGoogleUseCase.Output {
        return withContext(Dispatchers.IO) {
            authenticationRepository.signInWithGoogle()
        }
    }
}