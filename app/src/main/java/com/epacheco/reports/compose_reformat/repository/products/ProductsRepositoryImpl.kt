package com.epacheco.reports.compose_reformat.repository.products

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ProductsRepository {

    override suspend fun getProducts(): Resource<List<Product>> {
        val productList = mutableListOf<Product>()
        return try {
            getProductsReference().get().await().children.map { snapShot ->
                val product = snapShot.getValue(Product::class.java)
                product?.let {
                    productList.add(it)
                }
            }
            Resource.Success(productList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override fun getProductsReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE)

}
