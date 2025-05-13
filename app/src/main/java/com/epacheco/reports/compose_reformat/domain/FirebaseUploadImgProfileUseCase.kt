package com.epacheco.reports.compose_reformat.domain

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.user.UserRepositoryImp
import com.google.firebase.auth.FirebaseUser
import java.io.File
import javax.inject.Inject

class FirebaseUploadImgProfileUseCase @Inject constructor(private val userRepositoryImp: UserRepositoryImp) {

    suspend operator fun invoke(imageFile: File): Resource<Uri> {
        return userRepositoryImp.uploadProfileImage(imageFile)
    }

}