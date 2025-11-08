package com.epacheco.reports.compose_reformat.firebase


import com.google.android.gms.tasks.Task
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
            }
        }
    }
}
