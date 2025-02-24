package com.epacheco.reports.compose_reformat.repository.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : OrdersRepository {


    override suspend fun getOrders(): Resource<List<Order>> {
        val reference = firebaseDatabase.getReference("Reports").child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_ORDERS_TABLE_FIREBASE)
        val notes = mutableListOf<Order>()
        return try {
            reference.get().await().children.map { snapShot ->
                val order = snapShot.getValue(Order::class.java)
                order?.let {
                    notes.add(it)
                }
            }

            if (notes.isEmpty()) Resource.Failure(Exception("Lista vacia"))
            else Resource.Success(notes)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }
}
