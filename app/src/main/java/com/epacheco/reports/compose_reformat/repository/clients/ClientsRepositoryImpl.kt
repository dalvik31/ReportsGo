package com.epacheco.reports.compose_reformat.repository.clients

import android.annotation.SuppressLint
import android.util.Log
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.utils.DateUtils.dateFormat
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class ClientsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ClientsRepository {

    @SuppressLint("RestrictedApi")
    override suspend fun getClient(clientId: String): Resource<Client> {
        return try {
            val fullPath = getClientsReference().child(clientId).ref.path
            Log.e("FIREBASE_PATH", "Path exacto: $fullPath")

            val snapshot = getClientsReference()
                .child(clientId)
                .get()
                .await()

            val client = snapshot.getValue(Client::class.java)

            client?.let {
                it.dateClient = dateFormat(it.dateClient.toString())
                Resource.Success(it)
            } ?: Resource.Failure(Exception("Cliente $clientId no encontrado"))
        } catch (e: Exception) {
            Log.e("FIREBASE_ERROR", "Error obteniendo cliente: ${e.message}")
            Resource.Failure(e)
        }
    }

    override suspend fun getClients(paramName: String): Resource<List<Client>> {
        val clientList = mutableListOf<Client>()
        return try {
            getClientsReference().orderByChild(Constants.CLIENT_ORDER_PARAM_NAME_)
                .startAt(paramName).endAt(paramName + "\uf8ff").get()
                .await().children.map { snapShot ->
                    val client = snapShot.getValue(Client::class.java)
                    client?.let {
                        clientList.add(it)
                    }
                }
            Resource.Success(clientList)
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }

    }


    override fun getClientsReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_TABLE_FIREBASE)

}
