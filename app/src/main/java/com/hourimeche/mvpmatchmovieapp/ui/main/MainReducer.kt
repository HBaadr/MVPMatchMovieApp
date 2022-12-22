package com.hourimeche.mvpmatchmovieapp.ui.main

import com.hourimeche.mvpmatchmovieapp.redux.Reducer

/**
 * This reducer is responsible for handling any [MainAction], and using that to create
 * a new [MainViewState].
 */
class MainReducer : Reducer<MainViewState, MainAction> {

    /**
     * Side note: Notice that all of the functions are named in a way that they signify they're
     * returning a new state, and not just processing information. This helps keep your when statements
     * clear that they're returning stuff, so that context isn't lost.
     */
    override fun reduce(currentState: MainViewState, action: MainAction): MainViewState {
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
            is MainAction.GetMoviesWithTitle -> {
                currentState.copy(
                    title = action.title,
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