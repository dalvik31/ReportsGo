package com.epacheco.reports.compose_reformat.domain.products

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import java.io.File
import javax.inject.Inject

class UploadImgProductUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke(productImgFile: File, nameImgToReplace: String?): Resource<Uri> {
        return productsRepository.uploadProductImage(
            productImgFile,
            nameImgToReplace = nameImgToReplace
        )
    }

}