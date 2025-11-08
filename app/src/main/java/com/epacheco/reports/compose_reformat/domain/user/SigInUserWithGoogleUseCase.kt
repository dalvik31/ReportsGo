package com.epacheco.reports.compose_reformat.domain.user

import androidx.credentials.GetCredentialResponse
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SigInUserWithGoogleUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {
    suspend operator fun invoke(googleInfoAccount: GetCredentialResponse): Resource<FirebaseUser> {
        return firebaseAuthRepository.loginGoogle(googleInfoAccount)
    }
}