package com.epacheco.reports.compose_reformat.repository.clients

import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.model.clients.Client
import com.epacheco.reports.tools.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class ClientsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ClientsRepository {


    override suspend fun getClients(): Resource<List<Client>> {
        val clientList = mutableListOf<Client>()
        return try {
            getClientsReference().get().await().children.map { snapShot ->
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
