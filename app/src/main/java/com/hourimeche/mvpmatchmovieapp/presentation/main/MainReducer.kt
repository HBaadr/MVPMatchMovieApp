package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Reducer

/**
 * This reducer is responsible for handling any [MainAction], and using that to create
 * a new [MainState].
 */
class MainReducer : Reducer<MainState, MainAction> {

    /**
     * Side note: Notice that all of the functions are named in a way that they signify they're
     * returning a new state, and not just processing information. This helps keep your when statements
     * clear that they're returning stuff, so that context isn't lost.
     */
    override fun reduce(currentState: MainState, action: MainAction): MainState {
        return when (action) {
            // Add movie to favorite cache
            is MainAction.AddMovieToCache -> {
                currentState.copy(
                    movieRemoved = false,
                    id = action.moviesResponse.imdbID,
                )
            }


            // Remove movie from favorite cache
            is MainAction.RemoveMovieFromCache -> {
                currentState.copy(
                    movieRemoved = false,
                    id = action.moviesResponse.imdbID,
                )
            }
            MainAction.MovieRemoved -> {
                currentState.copy(
                    movieRemoved = true,
                )
            }

            // Error
            is MainAction.Error -> {
                currentState.copy(
                    movieRemoved = false,
                    showProgressBar = false,
                    errorMessage = action.errorMessage,
                )
            }

            // Get Movie by SEARCH
            is MainAction.SearchMovies -> {
                currentState.copy(
                    movieRemoved = false,
                    query = action.query,
                )
            }
            is MainAction.SuccessSearchMovies -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = action.searchResponse.Search?.isEmpty() ?: true,
                    movieRemoved = false,
                    cacheResponse = null,
                    searchResponse = action.searchResponse.Search,
                )
            }

            // Get movie by ID
            is MainAction.GetMovie -> {
                currentState.copy(
                    id = action.movieId,
                    moviesResponse = null,
                    movieRemoved = false,
                )
            }
            is MainAction.SuccessGetMovie -> {
                currentState.copy(
                    showProgressBar = false,
                    moviesResponse = action.moviesResponse,
                    movieRemoved = false,
                )
            }

            // Get favorite movie from CACHE
            MainAction.GetMoviesFromCache -> {
                currentState.copy(
                    movieRemoved = false,
                )
            }
            is MainAction.SuccessGetMoviesFromCache -> {
                currentState.copy(
                    showProgressBar = false,
                    movieRemoved = false,
                    isEmptyList = action.movies.isEmpty(),
                    searchResponse = null,
                    cacheResponse = action.movies,
                )
            }

            // Loader
            MainAction.Loading -> {
                currentState.copy(
                    movieRemoved = false,
                    showProgressBar = true,
                )
            }
            MainAction.FinishLoading -> {
                currentState.copy(
                    showProgressBar = false,
                    movieRemoved = false,
                )
            }
            MainAction.EmptyList -> {
                currentState.copy(
                    movieRemoved = false,
                    showProgressBar = false,
                    isEmptyList = true
                )
            }
            else -> currentState
        }
    }
}