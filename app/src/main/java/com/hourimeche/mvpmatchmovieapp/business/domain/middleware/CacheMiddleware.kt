package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.MovieDao
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.toMovieResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainAction
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainState

class CacheMiddleware(private val moviesService: MoviesService, private val movieDao: MovieDao) :
    Middleware<MainState, MainAction> {

    override suspend fun process(
        action: MainAction,
        currentState: MainState,
        store: Store<MainState, MainAction>,
    ) {
        when (action) {
            is MainAction.GetMoviesFromCache -> {
                getMoviesFromCache(store)
            }
            else -> {}
        }
    }

    private suspend fun getMoviesFromCache(store: Store<MainState, MainAction>) {
        store.dispatch(MainAction.Loading)

        val response = movieDao.getAllMovies()
        val movies = ArrayList<MovieResponse>()
        for (movie in response) {
            movies.add(movie?.toMovieResponse()!!)
        }
        if (response.isNotEmpty()) {
            store.dispatch(MainAction.SuccessGetMovies(movies))
        } else {
            store.dispatch(MainAction.Error("No movie found"))
        }
    }
}