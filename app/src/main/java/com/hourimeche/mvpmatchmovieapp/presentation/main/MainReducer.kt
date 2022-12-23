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
            is MainAction.Error -> {
                currentState.copy(
                    showProgressBar = false,
                    errorMessage = action.errorMessage,
                )
            }
            is MainAction.AddMovieToCache -> {
                currentState.copy(
                    id = action.moviesResponse.imdbID,
                )
            }
            is MainAction.RemoveMovieFromCache -> {
                currentState.copy(
                    id = action.moviesResponse.imdbID,
                )
            }
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
            is MainAction.SearchMovies -> {
                currentState.copy(
                    query = action.query,
                )
            }
            is MainAction.SuccessSearchMovies -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = action.searchResponse.Search?.isEmpty() ?: true,
                    cacheResponse = null,
                    searchResponse = action.searchResponse.Search,
                )
            }
            is MainAction.GetMovie -> {
                currentState.copy(
                    id = action.movieId,
                )
            }
            is MainAction.SuccessGetMovie -> {
                currentState.copy(
                    showProgressBar = false,
                    moviesResponse = action.moviesResponse,
                )
            }
            MainAction.GetMoviesFromCache -> {
                currentState.copy(
                )
            }
            is MainAction.SuccessGetMoviesFromCache -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = action.movies.isEmpty(),
                    searchResponse = null,
                    cacheResponse = action.movies,
                )
            }
            else -> currentState
        }
    }
}