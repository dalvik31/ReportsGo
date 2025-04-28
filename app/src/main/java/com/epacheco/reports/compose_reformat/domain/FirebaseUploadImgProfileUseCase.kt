package com.epacheco.reports.compose_reformat.domain

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.user.UserRepositoryImp
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseUploadImgProfileUseCase @Inject constructor(private val userRepositoryImp: UserRepositoryImp) {

    suspend operator fun invoke(imageUri: Uri): Resource<String> {
        return userRepositoryImp.uploadProfileImage(imageUri)
    }

}