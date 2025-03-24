package com.epacheco.reports.compose_reformat.firebase


import com.epacheco.reports.compose_reformat.model.products.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


suspend fun <T> Task<T>.await(): T {
    return suspendCoroutine { suspendCoroutine ->
        addOnCompleteListener {
            if (it.exception != null) {
                suspendCoroutine.resumeWithException(it.exception!!)
            } else {
                suspendCoroutine.resume(it.result)
                // suspendCoroutine.resume(it.result) { cause, _, _ -> null?.let { it1 -> it1(cause) } }
            }
        }
    }
}


@OptIn(InternalCoroutinesApi::class)
suspend fun <T> DatabaseReference.subscribeModifiedChildren(
    mapper: DataSnapshot.() -> T
): Flow<T> = callbackFlow {
    val valueEventListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            handleCoroutineException(coroutineContext, error.toException())
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            trySend(snapshot.mapper())
        }
    }
    addValueEventListener(valueEventListener)
    awaitClose {
        removeEventListener(valueEventListener)
    }
}


/*
suspend fun Query.read(): DataSnapshot = suspendCoroutine { continuation ->
    val valueEventListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            continuation.resumeWithException(error.toException())
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            continuation.resume(snapshot)
        }
    }
    addValueEventListener(valueEventListener)
}*/


suspend fun DatabaseReference.readData(): DataSnapshot {
    return suspendCancellableCoroutine { continuation ->
        val listener = object : ValueEventListener {
            var resumed = false // Flag to track if the continuation has been resumed

            override fun onDataChange(snapshot: DataSnapshot) {
                if (!resumed) {
                    //resumed = true
                    continuation.resume(snapshot)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                if (!resumed) {
                    //resumed = true
                    continuation.resumeWithException(error.toException())
                }
            }
        }

        addValueEventListener(listener)

        continuation.invokeOnCancellation {
            removeEventListener(listener)
        }
    }
}

suspend inline fun <reified T> Query.awaitQueryValue() : T = suspendCancellableCoroutine { continuation ->
    addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            snapshot.getValue(T::class.java)?.let { instance ->
                continuation.resume(instance)
            }
        }
        override fun onCancelled(error: DatabaseError) {
            continuation.resumeWithException(Throwable(error.message))
        }
    })
}

sealed class FlowDataState<out R> {
    data class Success<out T>(val data: T) : FlowDataState<T>()
    data class Error(val throwable: Throwable) : FlowDataState<Nothing>()
}

suspend inline fun <reified T> Query.awaitSingleValueEventList(): Flow<FlowDataState<List<T>>> =
    callbackFlow {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val entityList = mutableListOf<T>()
                    snapshot.children.forEach { dataSnapshot ->
                        dataSnapshot.getValue(T::class.java)?.let {
                            entityList.add(it)
                        }
                    }
                    trySend(FlowDataState.Success(entityList)).isSuccess
                } catch (e: DatabaseException) {
                    trySend(FlowDataState.Error(e)).isSuccess
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(FlowDataState.Error(error.toException())).isSuccess
            }
        }

        addListenerForSingleValueEvent(valueEventListener)

        awaitClose { removeEventListener(valueEventListener) }
    }