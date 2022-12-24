package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Action


/**
 * These are all of the possible actions that can be triggered from the login screen.
 */
sealed class MainAction : Action {
    // Add movie to favorite cache
    data class AddMovieToCache(val moviesResponse: Movie) : MainAction()

    // Remove movie from favorite cache
    data class RemoveMovieFromCache(val moviesResponse: Movie) : MainAction()
    object MovieRemoved : MainAction()

    // Get Movie by SEARCH
    data class SearchMovies(val query: String) : MainAction()
    data class SuccessSearchMovies(val searchResponse: List<Movie>) : MainAction()

    // Get favorite movie from CACHE
    data class SuccessGetMoviesFromCache(val movies: List<Movie>) : MainAction()
    object GetMoviesFromCache : MainAction()

    // Error
    data class Error(val errorMessage: String) : MainAction()

    // Add movie to unwonted cache
    class AddMovieToUnwanted(val moviesResponse: Movie) : MainAction()
    object SuccessAddMovieToUnwanted : MainAction()

    // Get unwanted movie from CACHE
    data class SuccessGetUnwantedMoviesFromCache(val movies: List<Movie>) : MainAction()
    object GetUnwantedMoviesFromCache : MainAction()

    // Loader
    object EmptyList : MainAction()
    object Loading : MainAction()
    object FinishLoading : MainAction()

}
