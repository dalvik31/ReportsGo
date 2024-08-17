package com.epacheco.reports.compose_reformat.dependecy_injection

import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.auth.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    internal fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    fun providesFirebaseAuthRepository(impl: FirebaseAuthRepository): AuthRepository = impl
}