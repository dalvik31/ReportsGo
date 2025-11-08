package com.epacheco.reports.compose_reformat.domain

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import com.epacheco.reports.compose_reformat.repository.user.UserRepositoryImp
import com.google.firebase.auth.FirebaseUser
import java.io.File
import javax.inject.Inject

class FirebaseUploadImgProductUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(productImgFile: File, nameImgToReplace: String?): Resource<Uri> {
        return productsRepository.uploadProductImage(productImgFile, nameImgToReplace = nameImgToReplace)
    }

}