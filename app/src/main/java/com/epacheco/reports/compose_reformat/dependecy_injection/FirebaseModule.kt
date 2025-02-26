package com.epacheco.reports.compose_reformat.dependecy_injection

import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepositoryImp
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepository
import com.epacheco.reports.compose_reformat.repository.clients.ClientsRepositoryImpl
import com.epacheco.reports.compose_reformat.repository.finances.FinancesRepository
import com.epacheco.reports.compose_reformat.repository.finances.FinancesRepositoryImpl
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepository
import com.epacheco.reports.compose_reformat.repository.orders.OrdersRepositoryImpl
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepository
import com.epacheco.reports.compose_reformat.repository.products.ProductsRepositoryImpl
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
    fun providesFirebaseAuthRepository(impl: AuthRepositoryImp): AuthRepository = impl

    @Provides
    fun providesFirebaseOrderRepository(
        impl: OrdersRepositoryImpl
    ): OrdersRepository = impl

    @Provides
    fun providesFirebaseClientsRepository(
        impl: ClientsRepositoryImpl
    ): ClientsRepository = impl

    @Provides
    fun providesFirebaseProductsRepository(
        impl: ProductsRepositoryImpl
    ): ProductsRepository = impl

    @Provides
    fun providesFirebaseFinancesRepository(
        impl: FinancesRepositoryImpl
    ): FinancesRepository = impl

}