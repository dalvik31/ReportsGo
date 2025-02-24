package com.epacheco.reports.compose_reformat.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException


@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { suspendCoroutine ->
        addOnCompleteListener {
            if (it.exception != null) {
                suspendCoroutine.resumeWithException(it.exception!!)
            } else {
                suspendCoroutine.resume(it.result, null)
            }
        }
    }
}
sealed class RealtimeDatabaseValueResult {
    class Success(val dataSnapshot: DataSnapshot): RealtimeDatabaseValueResult()
    class Error(val error: DatabaseError): RealtimeDatabaseValueResult()
}

/**
 * Perform a addListenerForSingleValueEvent call on a databaseReference in a suspend function way
 * @param onCancellation action to perform if there is a cancellation
 */
@ExperimentalCoroutinesApi
suspend fun DatabaseReference.awaitSingleValue(onCancellation: ((cause: Throwable) -> Unit)? = null) = suspendCancellableCoroutine<RealtimeDatabaseValueResult> { continuation ->

    val valueEventListener = object: ValueEventListener{
        override fun onCancelled(error: DatabaseError) {
            continuation.resume(RealtimeDatabaseValueResult.Error(error = error), onCancellation)
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            continuation.resume(RealtimeDatabaseValueResult.Success(snapshot), onCancellation)
        }
    }

    // add listener like you normally do
    addListenerForSingleValueEvent(valueEventListener)

    // in case the job, coroutine, etc. is cancelled, we remove the current event listener
    continuation.invokeOnCancellation { removeEventListener(valueEventListener) }
}