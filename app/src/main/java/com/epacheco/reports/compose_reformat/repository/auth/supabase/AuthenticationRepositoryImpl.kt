package com.epacheco.reports.compose_reformat.repository.auth.supabase

import com.epacheco.reports.compose_reformat.domain.user.supabase.CurrentUserUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInWithGoogleUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignUpUseCase
import com.epacheco.reports.compose_reformat.model.auth.AuthState
import com.epacheco.reports.compose_reformat.utils.extensions.toDomain
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.providers.builtin.Email
import javax.inject.Inject
import kotlin.time.ExperimentalTime


class AuthenticationRepositoryImpl @Inject constructor(
    private val auth: Auth
) : AuthenticationRepository {

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Initializing)
    override val authState: StateFlow<AuthState> = _authState


    override suspend fun currentUser(): CurrentUserUseCase.Output {
        return try {
            val currentUser = auth.currentUserOrNull()

            if (currentUser != null) {
                CurrentUserUseCase.Output.Success(currentUser.toDomain())
            } else {
                CurrentUserUseCase.Output.Failure(Exception())
            }

        } catch (e: Exception) {
            CurrentUserUseCase.Output.Failure(e)
        }
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        scope.launch {
            auth.sessionStatus.collect { status ->
                logSessionStatus(status)
            }
        }
    }

    override suspend fun signIn(email: String, password: String): SignInUseCase.Output {
        return try {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val currentUser = auth.currentUserOrNull()

            if (currentUser != null) {
                return SignInUseCase.Output.Success(currentUser.toDomain())
            } else {
                SignInUseCase.Output.Failure(Exception())
            }

        } catch (e: Exception) {
            SignInUseCase.Output.Failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): SignUpUseCase.Output {
        return try {
            auth.signUpWith(Email, "app://supabase.com/confirm") {
                this.email = email
                this.password = password
            }
            val currentUser = auth.currentUserOrNull()

            if (currentUser != null) {
                return SignUpUseCase.Output.Success(currentUser.toDomain())
            } else {
                SignUpUseCase.Output.Failure(Exception("No se pudo obtener la información del usuario"))
            }
        } catch (e: Exception) {
            SignUpUseCase.Output.Failure(e)
        }
    }

    override suspend fun signInWithGoogle(): SignInWithGoogleUseCase.Output {
        return try {
            auth.signInWith(Google)
            return SignInWithGoogleUseCase.Output.Success
        } catch (e: Exception) {
            SignInWithGoogleUseCase.Output.Failure(e)
        }
    }

    override suspend fun exchangeCodeForSession(code: String): Result<Unit> =
        runCatching {
            auth.exchangeCodeForSession(code = code, saveSession = true)
            return Result.success(Unit)
        }.onFailure {
            return Result.failure(it)
        }

    override suspend fun verifyEmail(tokenHash: String): Result<Unit> = runCatching {
        auth.verifyEmailOtp(
            type = OtpType.Email.EMAIL,
            tokenHash = tokenHash
        )
        return Result.success(Unit)
    }.onFailure { e ->
        return Result.failure(e)
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    @OptIn(ExperimentalTime::class)
    private fun logSessionStatus(sessionStatus: SessionStatus) {
        when (sessionStatus) {
            is SessionStatus.Authenticated -> {
                _authState.value = AuthState.Authenticated
            }


            SessionStatus.Initializing -> {
                _authState.value = AuthState.Initializing

            }


            is SessionStatus.RefreshFailure -> {

            }

            is SessionStatus.NotAuthenticated -> {
                _authState.value = AuthState.Unauthenticated
            }

        }
    }

}