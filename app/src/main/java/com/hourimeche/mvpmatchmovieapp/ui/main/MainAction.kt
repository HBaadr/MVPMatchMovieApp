package com.hourimeche.mvpmatchmovieapp.ui.main

import com.hourimeche.mvpmatchmovieapp.model.MovieResponse
import com.hourimeche.mvpmatchmovieapp.model.SearchResponse
import com.hourimeche.mvpmatchmovieapp.redux.Action


/**
 * These are all of the possible actions that can be triggered from the login screen.
 */
sealed class MainAction : Action {
    data class GetMoviesWithTitle(val title: String) : MainAction()
    data class Success(val moviesResponse: MovieResponse) : MainAction()
    data class SuccessMovies(val searchResponse: SearchResponse) : MainAction()
    data class Error(val errorMessage: String) : MainAction()
    object Loading : MainAction()
}
