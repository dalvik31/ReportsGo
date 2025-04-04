package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import javax.inject.Inject

class FirebaseRecoveryPasswordUserUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {
    suspend operator fun invoke(email: String): Resource<Boolean> {
        return firebaseAuthRepository.recoveryPassword(email)
    }

}