package com.epacheco.reports.compose_reformat.repository.finances

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.Finances.Sale
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class FinancesRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : FinancesRepository {


    override suspend fun getFinances(): Resource<List<Sale>> {
        val saleList = mutableListOf<Sale>()
        return try {
            getFinancesReference().get().await().children.map { snapShot ->
                val sale = snapShot.getValue(Sale::class.java)
                sale?.let {
                    saleList.add(it)
                }
            }
            Resource.Success(saleList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override fun getFinancesReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_SALES_TABLE_FIREBASE)

}
