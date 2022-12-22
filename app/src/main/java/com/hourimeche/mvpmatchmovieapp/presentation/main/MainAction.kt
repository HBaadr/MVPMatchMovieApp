package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.SearchResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Action


/**
 * These are all of the possible actions that can be triggered from the login screen.
 */
sealed class MainAction : Action {
    data class SearchMovies(val query: String) : MainAction()
    data class GetMovie(val movieId: String) : MainAction()
    data class Success(val moviesResponse: MovieResponse) : MainAction()
    data class SuccessMovies(val searchResponse: SearchResponse) : MainAction()
    data class Error(val errorMessage: String) : MainAction()
    object Loading : MainAction()
}
