package com.hourimeche.mvpmatchmovieapp.di

import android.content.Context
import com.hourimeche.mvpmatchmovieapp.business.domain.util.CheckNetworkConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CheckNetworkModule {

    @Provides
    @Singleton
    fun getCheckNetworkConnection(@ApplicationContext appContext: Context): CheckNetworkConnection {
        return CheckNetworkConnection(appContext)
    }
}
