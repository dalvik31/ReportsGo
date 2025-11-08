package com.epacheco.reports.compose_reformat.domain.user

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import javax.inject.Inject

class RecoveryPasswordUserUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {
    suspend operator fun invoke(email: String): Resource<Any> {
        return firebaseAuthRepository.recoveryPassword(email)
    }

}