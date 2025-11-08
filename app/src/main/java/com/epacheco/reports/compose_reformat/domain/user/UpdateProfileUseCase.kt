package com.epacheco.reports.compose_reformat.domain.user

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val firebaseAuthRepository: AuthRepository) {
    suspend operator fun invoke(uriImg: Uri?): Resource<Any> {
        return firebaseAuthRepository.updateImgProfile(uriImg)
    }
}