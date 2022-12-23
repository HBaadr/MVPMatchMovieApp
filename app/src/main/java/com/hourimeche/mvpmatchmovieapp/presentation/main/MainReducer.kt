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
                    id = action.moviesResponse.imdbID,
                )
            }

            // Add movie to unwanted cache
            is MainAction.AddMovieToUnwanted -> {
                currentState.copy(
                    id = action.moviesResponse.imdbID,
                    unwantedMovieAdded = false
                )
            }
            MainAction.SuccessAddMovieToUnwanted -> {
                currentState.copy(
                    unwantedMovieAdded = true
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
                    showProgressBar = false,
                    errorMessage = action.errorMessage,
                )
            }

            // Get Movie by SEARCH
            is MainAction.SearchMovies -> {
                currentState.copy(
                    query = action.query,
                    searchResponse = null,
                )
            }
            is MainAction.SuccessSearchMovies -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = action.searchResponse.Search?.isEmpty() ?: true,
                    searchResponse = action.searchResponse.Search,
                    isFavouriteScreen = false,
                )
            }

            // Get movie by ID
            is MainAction.GetMovie -> {
                currentState.copy(
                    id = action.movieId,
                    moviesResponse = null,
                )
            }
            is MainAction.SuccessGetMovie -> {
                currentState.copy(
                    showProgressBar = false,
                    moviesResponse = action.moviesResponse,
                )
            }

            // Get favorite movie from CACHE
            MainAction.GetMoviesFromCache -> {
                currentState.copy(
                    cacheResponse = null,
                )
            }
            is MainAction.SuccessGetMoviesFromCache -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = action.movies.isEmpty(),
                    cacheResponse = action.movies,
                    isFavouriteScreen = true,
                )
            }

            // Get unwanted movie from CACHE
            MainAction.GetUnwantedMoviesFromCache -> {
                currentState.copy(
                    // unwantedMovies = null,
                    unwantedMoviesReceived = false,
                )
            }
            is MainAction.SuccessGetUnwantedMoviesFromCache -> {
                currentState.copy(
                    showProgressBar = false,
                    unwantedMoviesReceived = true,
                    unwantedMovies = action.movies,
                )
            }

            // Loader
            MainAction.Loading -> {
                currentState.copy(
                    showProgressBar = true,
                )
            }
            MainAction.FinishLoading -> {
                currentState.copy(
                    showProgressBar = false,
                )
            }
            MainAction.EmptyList -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = true
                )
            }
            else -> currentState
        }
    }
}