package com.epacheco.reports.compose_reformat.domain.user

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import javax.inject.Inject

class SendEmailVerificationUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {

    suspend operator fun invoke(): Resource<Any> {
        return firebaseAuthRepository.sendEmailVerification()
    }
}