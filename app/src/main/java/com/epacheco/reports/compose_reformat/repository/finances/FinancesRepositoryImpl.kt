package com.epacheco.reports.compose_reformat.repository.finances

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.sales.Sale
import com.epacheco.reports.compose_reformat.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import javax.inject.Inject

class FinancesRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : FinancesRepository {

    override suspend fun getFinances(
        initialDate: String,
        finalDate: String
    ): Resource<List<Sale>> {
        val saleList = mutableListOf<Sale>()
        val usersRef = getFinancesReference()
        return try {
            var query: Query? = null

            query = usersRef.orderByChild("saleDate")
                .startAt(initialDate)
                .endAt(finalDate)

            query.get().await()?.children?.map { snapShot ->
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

    override suspend fun getFinancesByClientId(clientId: String): Resource<List<Sale>> {
        return try {
            val saleList = mutableListOf<Sale>()
            getFinancesReference().orderByChild("idClient").startAt(clientId)
                .endAt(clientId + "\uf8ff").get().await()?.children?.map { snapShot ->
                    val sale = snapShot.getValue(Sale::class.java)
                    sale?.let {
                        saleList.add(it)
                    }
                }
            saleList.sortByDescending {
                it.saleDate
            }
            Resource.Success(saleList)
        } catch (exception: Exception) {
            Resource.Success(emptyList())
        }
    }

    override fun getFinancesReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_SALES_TABLE_FIREBASE)

}
