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
            is MainAction.Success -> {
                currentState.copy(
                    showProgressBar = false,
                    moviesResponse = action.moviesResponse,
                )
            }
            is MainAction.SuccessMovies -> {
                currentState.copy(
                    showProgressBar = false,
                    searchResponse = action.searchResponse,
                )
            }
            is MainAction.GetMovie -> {
                currentState.copy(
                    id = action.movieId,
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
            else -> currentState
        }
    }
}