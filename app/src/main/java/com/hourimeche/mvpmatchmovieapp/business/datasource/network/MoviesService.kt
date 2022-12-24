package com.hourimeche.mvpmatchmovieapp.business.datasource.network

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.GenresResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MoviesResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("/3/search/multi?")
    suspend fun getMoviesBySearch(
        @Query("query") titleToSearch: String,
        @Query("api_key") apikey: String = Constants.API_KEY,
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("language") language: String = Constants.API_LANGUAGE,
        @Query("year") year: Int? = null
    ): Response<MoviesResponse>

    @GET("/3/genre/tv/list")
    suspend fun getTVGenres(
        @Query("api_key") apikey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.API_LANGUAGE
    ): Response<GenresResponse>

    @GET("/3/genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apikey: String = Constants.API_KEY,
        @Query("language") language: String = Constants.API_LANGUAGE
    ): Response<GenresResponse>
}