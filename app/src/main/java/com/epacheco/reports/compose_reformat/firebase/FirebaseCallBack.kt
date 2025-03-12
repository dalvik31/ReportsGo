package com.epacheco.reports.compose_reformat.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

abstract class FirebaseCallBack {
    abstract fun onSuccess(snapshot: DataSnapshot)
    abstract fun onError(error: DatabaseError)
}