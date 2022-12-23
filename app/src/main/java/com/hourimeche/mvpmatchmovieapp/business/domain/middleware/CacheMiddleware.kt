package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.MovieDao
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.toEntity
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.toMovieResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
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
            is MainAction.AddMovieToCache -> {
                addMoviesToFavorite(store, action.moviesResponse.imdbID)
            }
            is MainAction.RemoveMovieFromCache -> {
                removeMoviesFromFavorite(store, action.moviesResponse.imdbID)
            }
            else -> {}
        }
    }

    private suspend fun getMoviesFromCache(store: Store<MainState, MainAction>) {
        store.dispatch(MainAction.Loading)

        val response = movieDao.getAllFavoritesMovies()
        val movies = ArrayList<MovieResponse>()
        for (movie in response) {
            movies.add(movie?.toMovieResponse()!!)
        }
        store.dispatch(MainAction.SuccessGetMoviesFromCache(movies))
    }

    private suspend fun addMoviesToFavorite(store: Store<MainState, MainAction>, movieId: String) {
        store.dispatch(MainAction.Loading)

        val response = moviesService.getMovieById(
            movieId,
            Constants.API_KEY
        )

        if (response.isSuccessful) {
            movieDao.insertFavorite(response.body()?.toEntity(true, false)!!)
            store.dispatch(MainAction.FinishLoading)
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }

    }

    private suspend fun removeMoviesFromFavorite(
        store: Store<MainState, MainAction>,
        movieId: String
    ) {
        store.dispatch(MainAction.Loading)

        val response = moviesService.getMovieById(
            movieId,
            Constants.API_KEY
        )

        if (response.isSuccessful) {
            movieDao.deleteFavorite(response.body()?.toEntity(true, false)!!)
            store.dispatch(MainAction.FinishLoading)
            store.dispatch(MainAction.MovieRemoved)
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }

    }
}