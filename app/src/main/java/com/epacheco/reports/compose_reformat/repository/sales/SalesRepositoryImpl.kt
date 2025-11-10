package com.epacheco.reports.compose_reformat.repository.sales

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class SalesRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
) : SalesRepository {
    override suspend fun createSale(sale: Sale): Resource<Any> {
        return try {
            getSalesReference().child(sale.saleDate).setValue(sale)
            Resource.Success(Any())
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override fun getSalesReference(): DatabaseReference {
        return firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_SALES_TABLE_FIREBASE)
    }


}
