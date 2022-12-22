package com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_properties WHERE imdbID = :movieId")
    suspend fun searchById(movieId: String): MovieEntity?

    @Query("SELECT * FROM movie_properties")
    suspend fun getAllMovies(): List<MovieEntity?>

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(movieEntity: MovieEntity)

    //@Query("UPDATE movie_properties SET Title = :title WHERE imdbID = :movieId")
    //suspend fun updateMovieTitle(movieId: String, title: String)
}


















