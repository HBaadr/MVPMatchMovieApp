package com.hourimeche.mvpmatchmovieapp.data

import com.hourimeche.mvpmatchmovieapp.config.Constants
import com.hourimeche.mvpmatchmovieapp.model.MovieResponse
import com.hourimeche.mvpmatchmovieapp.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET(".")
    suspend fun getMoviesBySearch(
        @Query("s") titleToSearch: String,
        @Query("apikey") apikey: String = Constants.API_KEY
    ): Response<SearchResponse>

    @GET
    suspend fun getMovieById(
        @Query("i") idToSearch: String,
        @Query("apikey") apikey: String = Constants.API_KEY
    ): Response<MovieResponse>

    /*@GET(".")
    suspend fun getMovieByTitle(
        @Query("t") titleToSearch: String,
        @Query("apikey") apikey: String = Constants.API_KEY
    ): Response<MovieResponse>*/
}