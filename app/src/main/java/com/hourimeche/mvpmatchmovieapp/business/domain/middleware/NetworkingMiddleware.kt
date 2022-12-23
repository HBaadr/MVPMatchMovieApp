package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.SearchResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainAction
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainState

class NetworkingMiddleware(private val moviesService: MoviesService) :
    Middleware<MainState, MainAction> {

    override suspend fun process(
        action: MainAction,
        currentState: MainState,
        store: Store<MainState, MainAction>,
    ) {
        when (action) {
            is MainAction.SearchMovies -> {
                searchMovies(store, action.query)
            }
            is MainAction.GetMovie -> {
                getMovie(store, action.movieId)
            }
            else -> {}
        }
    }

    private suspend fun getMovie(
        store: Store<MainState, MainAction>,
        movieId: String,
    ) {
        store.dispatch(MainAction.Loading)

        val response = moviesService.getMovieById(
            movieId,
            Constants.API_KEY
        )

        if (response.isSuccessful) {
            store.dispatch(MainAction.SuccessGetMovie(response.body()!!))
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }
    }

    private suspend fun searchMovies(
        store: Store<MainState, MainAction>,
        query: String,
    ) {
        store.dispatch(MainAction.Loading)

        val response = moviesService.getMoviesBySearch(
            query,
            Constants.API_KEY
        )

        if (response.isSuccessful) {
            if (response.body()!!.Response) {
                val allMovies = ArrayList<MovieResponse>()
                response.body()!!.Search?.let { allMovies.addAll(it) }
                val moviesUnwanted = ArrayList<MovieResponse>()
                store.state.value.unwantedMovies?.let { moviesUnwanted.addAll(it) }
                for (movie in ArrayList<MovieResponse>(allMovies)) {
                    for (unwanted in moviesUnwanted) {
                        if (movie.imdbID == unwanted.imdbID)
                            allMovies.remove(movie)
                    }
                }
                store.dispatch(
                    MainAction.SuccessSearchMovies(
                        SearchResponse(
                            true,
                            allMovies,
                            null
                        )
                    )
                )
            } else
                store.dispatch(MainAction.EmptyList)
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }
    }
}