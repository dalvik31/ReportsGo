package com.epacheco.reports.compose_reformat.domain.user

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.user.UserRepository
import java.io.File
import javax.inject.Inject

class UploadImgProfileUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(imageFile: File): Resource<Uri> {
        return userRepository.uploadProfileImage(imageFile)
    }

}