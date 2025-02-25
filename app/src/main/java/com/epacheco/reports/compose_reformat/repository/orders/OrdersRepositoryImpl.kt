package com.epacheco.reports.compose_reformat.repository.orders

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : OrdersRepository {


    override suspend fun getOrders(): Resource<List<Order>> {
        val orderList = mutableListOf<Order>()
        return try {
            getOrdersReference().get().await().children.map { snapShot ->
                val order = snapShot.getValue(Order::class.java)
                order?.let {
                    orderList.add(it)
                }
            }
            Resource.Success(orderList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override fun getOrdersReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_ORDERS_TABLE_FIREBASE)

}
