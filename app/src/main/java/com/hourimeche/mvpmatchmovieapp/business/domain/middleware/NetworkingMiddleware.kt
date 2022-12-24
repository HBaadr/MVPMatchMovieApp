package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
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
            else -> {}
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
            if (response.body()!!.results.isNotEmpty()) {
                val allMovies = ArrayList<Movie>()
                response.body()!!.results.let { allMovies.addAll(it) }
                val moviesUnwanted = ArrayList<Movie>()
                store.state.value.unwantedMovies?.let { moviesUnwanted.addAll(it) }
                for (movie in ArrayList<Movie>(allMovies)) {
                    for (unwanted in moviesUnwanted) {
                        if (movie.id == unwanted.id)
                            allMovies.remove(movie)
                    }
                }
                store.dispatch(
                    MainAction.SuccessSearchMovies(
                        allMovies
                    )
                )
            } else
                store.dispatch(MainAction.EmptyList)
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }
    }
}