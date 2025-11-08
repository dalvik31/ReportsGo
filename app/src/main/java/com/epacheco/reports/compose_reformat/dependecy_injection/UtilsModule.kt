package com.epacheco.reports.compose_reformat.dependecy_injection

import android.content.Context
import com.epacheco.reports.compose_reformat.ReportsApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun providesMainApplicationInstance(@ApplicationContext context: Context): ReportsApp {
        return context as ReportsApp
    }
}