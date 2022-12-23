package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.SearchResponse
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
            is MainAction.SuccessGetMovie -> {
                currentState.copy(
                    showProgressBar = false,
                    moviesResponse = action.moviesResponse,
                )
            }
            is MainAction.SuccessSearch -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = action.searchResponse.Search?.isEmpty() ?: true,
                    searchResponse = action.searchResponse,
                )
            }
            is MainAction.GetMovie -> {
                currentState.copy(
                    id = action.movieId,
                )
            }
            is MainAction.AddMovieToCache -> {
                currentState.copy(
                    id = action.moviesResponse.imdbID,
                )
            }
            is MainAction.SearchMovies -> {
                currentState.copy(
                    query = action.query,
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
            MainAction.GetMoviesFromCache -> {
                currentState.copy(
                )
            }
            MainAction.EmptyList -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = true
                )
            }
            is MainAction.SuccessGetMovies -> {
                currentState.copy(
                    showProgressBar = false,
                    isEmptyList = action.movies.isEmpty(),
                    searchResponse = SearchResponse(
                        Search = action.movies,
                        totalResults = null,
                        Response = true
                    ),
                )
            }
            else -> currentState
        }
    }
}