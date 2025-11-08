package com.epacheco.reports.compose_reformat.domain.user

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {

    operator fun invoke(): Resource<Any> {
        return firebaseAuthRepository.logout()
    }
}