package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.SearchResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Action


/**
 * These are all of the possible actions that can be triggered from the login screen.
 */
sealed class MainAction : Action {
    object GetMoviesFromCache : MainAction()
    data class AddMovieToCache(val moviesResponse: MovieResponse) : MainAction()
    data class RemoveMovieFromCache(val moviesResponse: MovieResponse) : MainAction()
    data class SearchMovies(val query: String) : MainAction()
    data class GetMovie(val movieId: String) : MainAction()
    data class SuccessGetMovie(val moviesResponse: MovieResponse) : MainAction()
    data class SuccessSearch(val searchResponse: SearchResponse) : MainAction()
    data class SuccessGetMovies(val movies: List<MovieResponse>) : MainAction()
    data class Error(val errorMessage: String) : MainAction()
    object EmptyList : MainAction()
    object Loading : MainAction()
    object FinishLoading : MainAction()
}
