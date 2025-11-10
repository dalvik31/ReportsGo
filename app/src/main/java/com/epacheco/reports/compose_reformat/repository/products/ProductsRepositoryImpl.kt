package com.epacheco.reports.compose_reformat.repository.products

import android.net.Uri
import androidx.core.net.toUri
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage
) : ProductsRepository {
    override suspend fun getProductsById(productId: String?): Resource<Product> {
        var product: Product? = null
        return try {
            getProductsReference().orderByChild("productId").equalTo(productId).get()
                .await().children.map {
                    product = it.getValue(Product::class.java)
                }
            Resource.Success(product!!)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun getProductsByName(productName: String?): Resource<List<Product>> {
        return try {
            val productList = mutableListOf<Product>()
            getProductsReference().orderByChild("productName").startAt(productName)
                .endAt(productName + "\uf8ff").get().await().children.map { snapShot ->
                    val product = snapShot.getValue(Product::class.java)
                    product?.let {
                        productList.add(it)
                    }
                }
            Resource.Success(productList)
        } catch (exception: Exception) {
            Resource.Success(emptyList())
        }
    }

    override suspend fun uploadProductImage(
        imageFile: File,
        nameImgToReplace: String?
    ): Resource<Uri> {
        return try {
            val uploadTask =
                getStorageReference(nameImgToReplace)?.putFile(imageFile.toUri())?.await()
            val downloadUrlImg = uploadTask?.storage?.downloadUrl?.await()
            imageFile.delete()
            Resource.Success(downloadUrlImg.toString().toUri())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateProduct(product: Product): Resource<Any> {
        return try {
            getProductsReference().child(product.productId).setValue(product)
            Resource.Success(Any())
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun updateStockProduct(
        productId: String,
        newStock: Int
    ): Resource<Any> {
        return try {
            getProductsReference().child(productId).child("inStock").setValue(newStock)
            Resource.Success(Any())
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun createProduct(product: Product): Resource<Any> {
        return try {
            getProductsReference().child(product.productId).setValue(product)
            Resource.Success(Any())
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun deleteProduct(productId: String): Resource<Any> {
        return try {
            getProductsReference().child(productId).removeValue()
            Resource.Success(Any())
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun deleteImgProduct(imgName: String?): Resource<Any> {
        return try {
            getStorageReference(imgName)?.delete()
            Resource.Success(Any())
        } catch (e: Exception) {
            Resource.Failure(e)
        }

    }


    override fun getProductsReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE)

    override fun getStorageReference(nameImgToReplace: String?): StorageReference? {
        val nameImgFile =
            if (nameImgToReplace.isNullOrEmpty()) "${System.currentTimeMillis()}.jpg" else nameImgToReplace
        return firebaseAuth.uid?.let { userId ->
            firebaseStorage.getReference().child(Constants.DATABASE_FIREBASE_NAME)
                .child(userId)
                .child(Constants.CLIENT_IMAGES_TABLE_FIREBASE)
                .child(nameImgFile)
        }
    }


}
