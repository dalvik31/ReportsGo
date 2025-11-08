package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseUserSignUpUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {

    suspend operator fun invoke(userEmail: String, userPassword: String): Resource<FirebaseUser> {
        return firebaseAuthRepository.signup(userEmail, userPassword)
    }
}