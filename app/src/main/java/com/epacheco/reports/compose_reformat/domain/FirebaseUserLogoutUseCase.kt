package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import javax.inject.Inject

class FirebaseUserLogoutUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {

     operator fun invoke() {
        return firebaseAuthRepository.logout()
    }
}