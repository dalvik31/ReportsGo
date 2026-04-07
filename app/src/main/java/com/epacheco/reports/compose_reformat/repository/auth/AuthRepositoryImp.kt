package com.epacheco.reports.compose_reformat.repository.auth

import android.net.Uri
import androidx.credentials.GetCredentialResponse
import com.epacheco.reports.R
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.firebase.Resource
import com.epacheco.reports.compose_reformat.firebase.await
import com.epacheco.reports.compose_reformat.utils.extensions.getNameFromEmail
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val app: ReportsApp
) :
    AuthRepository {
    override fun getCurrentUser(): Resource<FirebaseUser> {
        return if (firebaseAuth.currentUser != null) Resource.Success(firebaseAuth.currentUser!!) else Resource.Failure(
            Exception(app.getString(R.string.msg_user_profile_not_found))
        )
    }

    override suspend fun loginGoogle(googleInfoAccount: GetCredentialResponse): Resource<FirebaseUser> {
        return try {
            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(googleInfoAccount.credential.data)
            val credential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun signup(
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(email.getNameFromEmail()).build()
            )?.await()
            result.user?.sendEmailVerification()?.await()
            Resource.Success(result.user!!)
        } catch (customException: Exception) {
            Resource.Failure(customException)
        }
    }

    override suspend fun recoveryPassword(email: String): Resource<Any> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success(Any())
        } catch (customException: Exception) {
            Resource.Failure(customException)
        }
    }

    override suspend fun updateImgProfile(uriImg: Uri?): Resource<Any> {
        return try {
            val profileImgUpdate = UserProfileChangeRequest.Builder()
                .setPhotoUri(uriImg)
                .build()
            firebaseAuth.currentUser?.updateProfile(profileImgUpdate)?.await()
            Resource.Success(Any())
        } catch (customException: Exception) {
            Resource.Failure(customException)
        }
    }

    override suspend fun sendEmailVerification(): Resource<Any> {
        return try {
            val result = firebaseAuth.currentUser
            result?.sendEmailVerification()?.await()
            Resource.Success(Any())
        } catch (customException: Exception) {
            Resource.Failure(customException)
        }
    }

    override fun logout(): Resource<Any> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(Any())
        } catch (customException: Exception) {
            Resource.Failure(customException)
        }
    }

}