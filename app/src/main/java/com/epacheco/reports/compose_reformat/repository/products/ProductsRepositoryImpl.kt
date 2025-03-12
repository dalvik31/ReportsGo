package com.epacheco.reports.compose_reformat.repository.products

import com.epacheco.reports.compose_reformat.firebase.FirebaseCallBack
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ProductsRepository {
    /*override suspend fun getProducts(): Resource<List<Product>> {
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
    }*/

    override suspend fun getProducts(): Flow<List<Product>> {
        val productList = mutableListOf<Product>()
        return getProductsReference().listen().map {
            val (data, error) = it
            if (error != null) {
                emptyList()
            } else {
                data?.children?.mapNotNull { snapshot ->
                    val product = snapshot.getValue(Product::class.java)
                    product?.let {
                        productList.add(it)
                    }
                }
                productList
            }
        }

        /* val productList = mutableListOf<Product>()
         return try {

             Resource.Success(productList)
         } catch (exception: Exception) {
             Resource.Failure(exception)
         }*/
    }

    override suspend fun getProducts(firebaseCallBack: FirebaseCallBack) {
        getProductsReference().addChildEventListener(object : ChildEventListener {


            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                firebaseCallBack.onSuccess(snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                firebaseCallBack.onSuccess(snapshot)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                firebaseCallBack.onSuccess(snapshot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                firebaseCallBack.onSuccess(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                firebaseCallBack.onError(error)
            }

        })
    }


    override fun getUser(): Flow<List<Product>?> {


        val productList = mutableListOf<Product>()
        return callbackFlow {
            // Reference the user node in the database
            val userRef = getProductsReference()

            // Add a listener to the user node
            val listener = userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Emit the user data to the flow
                    productList.clear()
                    dataSnapshot.children.map { snapShot ->
                        val product = snapShot.getValue(Product::class.java)
                        product?.let {
                            productList.add(it)
                        }
                    }
                    trySend(productList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Cancel the flow on error
                    cancel()
                }
            })
            // Return the listener to be used to cancel the flow
            awaitClose { userRef.removeEventListener(listener) }
        }
    }

    data class SnapshotResult(val snapshot: DataSnapshot? = null, val error: Exception? = null)

    private fun DatabaseReference.listen(): Flow<SnapshotResult> =
        callbackFlow {
            val valueListener = object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    close(databaseError.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(SnapshotResult(snapshot, null))
                }
            }
            addValueEventListener(valueListener)

            awaitClose { removeEventListener(valueListener) }
        }

    override fun getProductsReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_PRODUCTS_TABLE_FIREBASE)

    // Fetch all notes related to the current user only
    override suspend fun getAllProducts(): Flow<Result<List<Product>>> = flow {
        val productList = mutableListOf<Product>()
        try {
            // Query notes where userId matches the current user's ID


            getProductsReference().get().await().children.mapNotNull {
                val product = it.getValue(Product::class.java)
                product?.let {
                    productList.add(product)
                }
            }


            emit(Result.success(productList))  // Emit success result with notes
        } catch (e: Exception) {
            emit(Result.failure(e))  // Emit failure result in case of error
        }
    }.catch { e ->
        emit(Result.failure(e)) // Handle any exception
    }

}
