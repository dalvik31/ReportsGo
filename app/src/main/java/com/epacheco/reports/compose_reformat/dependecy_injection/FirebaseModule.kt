package com.epacheco.reports.compose_reformat.dependecy_injection

import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.auth.FirebaseAuthRepository
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    internal fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    internal fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    fun providesFirebaseAuthRepository(impl: FirebaseAuthRepository): AuthRepository = impl

    @Provides
    fun providesFirebaseDatabaseRepository(
        impl: OrdersRepositoryImpl
    ): OrdersRepository = impl

}