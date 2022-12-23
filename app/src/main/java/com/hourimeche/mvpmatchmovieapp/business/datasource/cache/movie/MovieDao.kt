package com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_properties WHERE imdbID = :movieId")
    suspend fun searchById(movieId: String): MovieEntity?

    @Query("SELECT * FROM movie_properties WHERE isFavorite = 1")
    suspend fun getAllFavoritesMovies(): List<MovieEntity?>

    @Delete
    suspend fun deleteFavorite(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movieEntity: MovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM movie_properties WHERE imdbID = :movieId AND isFavorite = 1)")
    suspend fun isMovieInFavouriteList(movieId: String): Boolean

    @Query("SELECT * FROM movie_properties WHERE isUnwanted = 1")
    suspend fun getAllUnwantedMovies(): List<MovieEntity?>

    @Delete
    suspend fun deleteUnwanted(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnwanted(movieEntity: MovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM movie_properties WHERE imdbID = :movieId AND isUnwanted = 1)")
    suspend fun isMovieInUnwantedList(movieId: String): Boolean

    //@Query("UPDATE movie_properties SET Title = :title WHERE imdbID = :movieId")
    //suspend fun updateMovieTitle(movieId: String, title: String)
}


















