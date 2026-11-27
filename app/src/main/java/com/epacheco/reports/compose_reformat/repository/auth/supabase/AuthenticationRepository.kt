package com.epacheco.reports.compose_reformat.repository.auth.supabase

import com.epacheco.reports.compose_reformat.domain.user.supabase.CurrentUserUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignOutUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInWithGoogleUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignUpUseCase
import com.epacheco.reports.compose_reformat.model.auth.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepository {
    val authState: StateFlow<AuthState>
    suspend fun currentUser(): CurrentUserUseCase.Output
    suspend fun signIn(email: String, password: String): SignInUseCase.Output
    suspend fun signUp(email: String, password: String): SignUpUseCase.Output
    suspend fun signInWithGoogle(): SignInWithGoogleUseCase.Output
    suspend fun exchangeCodeForSession(code: String): Result<Unit>
    suspend fun verifyEmail(tokenHash: String): Result<Unit>
    suspend fun signOut(): SignOutUseCase.Output
}
