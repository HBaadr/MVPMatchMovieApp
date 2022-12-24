package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.State

/**
 * An implementation of [State] that describes the configuration of the login screen at a given time.
 */
data class MainState(
    val query: String = "",
    val id: Int = -1,
    val showProgressBar: Boolean = false,
    val errorMessage: String? = null,
    val searchResponse: List<Movie>? = null,
    val cacheResponse: List<Movie>? = null,
    val unwantedMovies: List<Movie>? = null,
    val isEmptyList: Boolean = true,
    val movieRemoved: Boolean = false,
    val unwantedMovieAdded: Boolean = false,
    val unwantedMoviesReceived: Boolean = false,
    val isFavouriteScreen: Boolean = true,
) : State