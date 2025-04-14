package com.epacheco.reports.compose_reformat.repository.orders

import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.orders.Order
import com.epacheco.reports.compose_reformat.model.orders.OrderMain
import com.epacheco.reports.compose_reformat.model.orders.OrderStatus
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val application: ReportsApp,
) : OrdersRepository {
    override suspend fun createOrder(order: Order): Resource<Boolean> {
        var createOrderException: Exception? = null
        return try {
            getOrdersReference().child(order.orderListId).child("orderLists").child(order.orderId)
                .setValue(
                    order
                ) { error, ref ->
                    error?.let {
                        createOrderException = it.toException()
                    }
                }
            createOrderException?.let {
                Resource.Failure(it)
            } ?: run {
                Resource.Success(true)
            }

        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun deleteOrder(orderId: String, mainOrderId: String): Resource<Boolean> {
        var deleteOrderException: Exception? = null
        return try {
            getOrdersReference().child(mainOrderId).child("orderLists").child(orderId).removeValue()
                .addOnFailureListener { e -> deleteOrderException = e }

            deleteOrderException?.let {
                Resource.Failure(it)
            } ?: run {
                Resource.Success(true)
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun updateOrder(order: Order): Resource<Boolean> {
        var updateOrderException: Exception? = null
        return try {
            getOrdersReference().child(order.orderListId).child("orderLists").child(order.orderId)
                .setValue(order)
                .addOnSuccessListener { }
                .addOnFailureListener { e -> updateOrderException = e }
            updateOrderException?.let {
                Resource.Failure(it)
            } ?: run {
                Resource.Success(true)
            }
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun updateStatusOrder(
        orderId: String,
        mainOrderId: String,
        orderBuy:Boolean
    ): Resource<Boolean> {
        var updateOrderStatusException: Exception? = null
        return try {
            getOrdersReference().child(mainOrderId).child("orderLists").child(orderId)
                .child("orderBuy").setValue(orderBuy)
                .addOnSuccessListener { }
                .addOnFailureListener { e -> updateOrderStatusException = e }
            updateOrderStatusException?.let {
                Resource.Failure(it)
            } ?: run {
                Resource.Success(true)
            }
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun getOrders(mainOrderId: String): Resource<List<Order>> {
        val orderMainList = mutableListOf<Order>()
        return try {
            getOrdersReference().child(mainOrderId).child("orderLists").get()
                .await().children.map { snapShot ->
                    val order = snapShot.getValue(Order::class.java)
                    order?.let {
                        orderMainList.add(it)
                    }
                }
            orderMainList.sortByDescending {
                it.orderId
            }
            orderMainList.sortBy {
                it.orderBuy
            }
            Resource.Success(orderMainList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }


    override suspend fun getMainOrders(): Resource<List<OrderMain>> {
        val orderMainList = mutableListOf<OrderMain>()
        return try {
            getOrdersReference().get().await().children.map { snapShot ->
                val orderMain = snapShot.getValue(OrderMain::class.java)
                orderMain?.let {
                    orderMainList.add(it)
                }
            }
            orderMainList.reverse()
            Resource.Success(orderMainList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun deleteMainOrder(mainOrderId: String): Resource<Boolean> {
        var deleteMainOrderException: Exception? = null
        return try {
            getOrdersReference().child(mainOrderId).removeValue()
                .addOnFailureListener { e -> deleteMainOrderException = e }

            deleteMainOrderException?.let {
                Resource.Failure(it)
            } ?: run {
                Resource.Success(true)
            }
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


    override suspend fun createMainOrder(
        newOrderMain: OrderMain,
        addCreateRestriction: Boolean
    ): Resource<Boolean> {
        var createOrderException: Exception? = null
        return try {
            if (addCreateRestriction) {
                getOrdersReference().orderByChild("orderDate").equalTo(newOrderMain.orderDate).get()
                    .await().children.map { snapShot ->
                        if (snapShot.exists()) {
                            createOrderException =
                                Exception(application.getString(R.string.msg_error_list_already_exist))
                        }
                    }
            }
            createOrderException?.let {
                Resource.Failure(it)
            } ?: run {
                getOrdersReference().child(newOrderMain.orderId).setValue(
                    newOrderMain
                ) { error, ref ->
                    error?.let {
                        createOrderException = it.toException()
                    }
                }
                createOrderException?.let {
                    Resource.Failure(it)
                } ?: run {
                    Resource.Success(true)
                }

            }

        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun updateStatusMainOrder(
        orderId: String,
        orderStatus: OrderStatus
    ): Resource<Boolean> {
        var createOrderException: Exception? = null
        return try {
            getOrdersReference().child(orderId).child("orderStatus").setValue(orderStatus.name)
                .addOnSuccessListener { }
                .addOnFailureListener { e -> createOrderException = e }
            createOrderException?.let {
                Resource.Failure(it)
            } ?: run {
                Resource.Success(true)
            }
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }

    }


    override fun getOrdersReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_ORDERS_TABLE_FIREBASE)


}
