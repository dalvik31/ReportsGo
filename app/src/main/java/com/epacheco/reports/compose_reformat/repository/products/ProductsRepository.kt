package com.epacheco.reports.compose_reformat.repository.products

import android.net.Uri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import java.io.File

interface ProductsRepository {
    suspend fun getProductsById(productId: String?): Resource<Product>
    suspend fun getProductsByName(productName: String?): Resource<List<Product>>
    suspend fun uploadProductImage(imageFile: File, nameImgToReplace: String? = null): Resource<Uri>
    suspend fun updateProduct(product: Product): Resource<Any>
    suspend fun updateStockProduct(productId: String, newStock: Int): Resource<Any>
    suspend fun createProduct(product: Product): Resource<Any>
    suspend fun deleteProduct(productId: String): Resource<Any>
    suspend fun deleteImgProduct(imgName: String?): Resource<Any>
    fun getProductsReference(): DatabaseReference
    fun getStorageReference(nameImgToReplace: String? = null): StorageReference?

}