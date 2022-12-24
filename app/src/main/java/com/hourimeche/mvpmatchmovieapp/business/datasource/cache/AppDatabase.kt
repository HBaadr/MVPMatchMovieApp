package com.hourimeche.mvpmatchmovieapp.business.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.MovieDao
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.MovieEntity

@Database(entities = [MovieEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {
        val DATABASE_NAME: String = "MVP_MATCH"
    }


}








