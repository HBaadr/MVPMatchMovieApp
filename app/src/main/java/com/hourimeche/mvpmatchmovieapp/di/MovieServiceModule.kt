package com.hourimeche.mvpmatchmovieapp.di

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieServiceModule {

    @Provides
    @Singleton
    fun getMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}
