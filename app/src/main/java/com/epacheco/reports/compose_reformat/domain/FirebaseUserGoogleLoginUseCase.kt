package com.epacheco.reports.compose_reformat.domain

import androidx.credentials.GetCredentialResponse
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseUserGoogleLoginUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {
    suspend operator fun invoke(googleInfoAccount: GetCredentialResponse): Resource<FirebaseUser> {
        return firebaseAuthRepository.loginGoogle(googleInfoAccount)
    }
}