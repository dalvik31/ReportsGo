package com.epacheco.reports.compose_reformat.domain

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import javax.inject.Inject

class FirebaseUpdateProfileUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {
    suspend operator fun invoke(uriImg: Uri?): Resource<Any> {
        return firebaseAuthRepository.updateImgProfile(uriImg)
    }
}