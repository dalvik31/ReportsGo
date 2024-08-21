package com.epacheco.reports.compose_reformat.dependecy_injection

import android.app.Application
import android.content.Context
import com.epacheco.reports.compose_reformat.ReportsApp
import com.epacheco.reports.compose_reformat.repository.auth.AuthRepository
import com.epacheco.reports.compose_reformat.repository.auth.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun providesMainApplicationInstance(@ApplicationContext context: Context): ReportsApp {
        return context as ReportsApp
    }
}