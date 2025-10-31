package com.epacheco.reports.compose_reformat.repository.clients

import android.annotation.SuppressLint
import android.util.Log
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.compose_reformat.model.products.Product
import com.epacheco.reports.compose_reformat.utils.DateUtils
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
            val snapshot = getClientsReference()
                .child(clientId)
                .get()
                .await()

            val client = snapshot.getValue(Client::class.java)

            client?.let { Resource.Success(it)
            } ?: Resource.Failure(Exception("Cliente $clientId no encontrado"))
        } catch (e: Exception) {
            Log.e("FIREBASE_ERROR", "Error obteniendo cliente: ${e.message}")
            Resource.Failure(e)
        }
    }

    override suspend fun updateClientLimit(
        clientId: String,
        newLimit: Double,
        newLimitUsed: Double
    ): Resource<Any> {
        getClientsReference().child(clientId).child("limit").setValue(newLimit)
        getClientsReference().child(clientId).child("limitUsed").setValue(newLimitUsed)
        return Resource.Success(Any())
    }

    override suspend fun updateClientDebt(
        clientId: String,
        newDebt: Double
    ): Resource<Any> {
        getClientsReference().child(clientId).child("debt").setValue(newDebt)
        return Resource.Success(Any())
    }

    override suspend fun updateClient(client: Client): Resource<Any> {
        return try {
            getClientsReference().child(client.id).setValue(client)
            Resource.Success(Any())
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun createClient(client: Client): Resource<Any> {
        return try {
            getClientsReference().child(client.id).setValue(client)
            Resource.Success(Any())
        } catch (exception: Exception) {
            Resource.Failure(exception)
        }
    }

    override suspend fun deleteClient(clientId: String): Resource<Any> {
        return try {
            getClientsReference().child(clientId).removeValue()
            Resource.Success(Any())
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun getClientByName(clientName: String?): Resource<List<Client>> {
        return try {
            val clientList = mutableListOf<Client>()
            getClientsReference().orderByChild("name").startAt(clientName)
                .endAt(clientName + "\uf8ff").get().await().children.map { snapShot ->
                    val client = snapShot.getValue(Client::class.java)
                    client?.let {
                        clientList.add(it)
                    }
                }
            Resource.Success(clientList)
        } catch (exception: Exception) {
            Resource.Success(emptyList())
        }
    }


    override fun getClientsReference(): DatabaseReference =
        firebaseDatabase.getReference(Constants.DATABASE_FIREBASE_NAME)
            .child(firebaseAuth.uid ?: "")
            .child(Constants.CLIENT_TABLE_FIREBASE)

}
