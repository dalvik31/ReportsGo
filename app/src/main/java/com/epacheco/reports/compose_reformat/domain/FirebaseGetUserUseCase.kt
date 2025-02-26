package com.epacheco.reports.compose_reformat.domain

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseGetUserUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {

    operator fun invoke(): Resource<FirebaseUser>? {
        return firebaseAuthRepository.getCurrentUser()
    }

}