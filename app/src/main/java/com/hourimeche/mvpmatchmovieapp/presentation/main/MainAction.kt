package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.SearchResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Action


/**
 * These are all of the possible actions that can be triggered from the login screen.
 */
sealed class MainAction : Action {
    // Add movie to favorite cache
    data class AddMovieToCache(val moviesResponse: MovieResponse) : MainAction()

    // Remove movie from favorite cache
    data class RemoveMovieFromCache(val moviesResponse: MovieResponse) : MainAction()
    object MovieRemoved : MainAction()

    // Get Movie by SEARCH
    data class SearchMovies(val query: String) : MainAction()
    data class SuccessSearchMovies(val searchResponse: SearchResponse) : MainAction()

    // Get movie by ID
    data class GetMovie(val movieId: String) : MainAction()
    data class SuccessGetMovie(val moviesResponse: MovieResponse) : MainAction()

    // Get favorite movie from CACHE
    data class SuccessGetMoviesFromCache(val movies: List<MovieResponse>) : MainAction()
    object GetMoviesFromCache : MainAction()

    // Error
    data class Error(val errorMessage: String) : MainAction()

    // Loader
    object EmptyList : MainAction()
    object Loading : MainAction()
    object FinishLoading : MainAction()

}
