package com.hourimeche.mvpmatchmovieapp.di


import android.app.Application
import androidx.room.Room
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.AppDatabase
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao {
        return db.getMovieDao()
    }

}