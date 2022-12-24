package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.MovieDao
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.toEntity
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.toMovie
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainAction
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainState

class CacheMiddleware(private val movieDao: MovieDao) :
    Middleware<MainState, MainAction> {

    override suspend fun process(
        action: MainAction,
        currentState: MainState,
        store: Store<MainState, MainAction>,
    ) {
        when (action) {
            is MainAction.GetMoviesFromCache -> {
                getFavoritesMoviesFromCache(store)
            }
            is MainAction.GetUnwantedMoviesFromCache -> {
                getUnwantedMoviesFromCache(store)
            }
            is MainAction.AddMovieToCache -> {
                addMoviesToFavorite(store, action.moviesResponse)
            }
            is MainAction.RemoveMovieFromCache -> {
                removeMoviesFromFavorite(store, action.moviesResponse)
            }
            is MainAction.AddMovieToUnwanted -> {
                addMoviesToUnwanted(store, action.moviesResponse)
            }
            else -> {}
        }
    }

    private suspend fun getFavoritesMoviesFromCache(store: Store<MainState, MainAction>) {
        store.dispatch(MainAction.Loading)

        val response = movieDao.getAllFavoritesMovies()
        val movies = ArrayList<Movie>()
        for (movie in response) {
            movies.add(movie?.toMovie()!!)
        }
        store.dispatch(MainAction.SuccessGetMoviesFromCache(movies))
    }

    private suspend fun getUnwantedMoviesFromCache(store: Store<MainState, MainAction>) {
        store.dispatch(MainAction.Loading)

        val response = movieDao.getAllUnwantedMovies()
        val movies = ArrayList<Movie>()
        for (movie in response) {
            movies.add(movie?.toMovie()!!)
        }
        store.dispatch(MainAction.SuccessGetUnwantedMoviesFromCache(movies))
    }

    private suspend fun addMoviesToFavorite(store: Store<MainState, MainAction>, movie: Movie) {
        store.dispatch(MainAction.Loading)

        movieDao.insertFavorite(movie.toEntity(true, false))
        store.dispatch(MainAction.FinishLoading)

    }

    private suspend fun addMoviesToUnwanted(store: Store<MainState, MainAction>, movie: Movie) {
        store.dispatch(MainAction.Loading)

        movieDao.insertUnwanted(movie.toEntity(false, true))
        store.dispatch(MainAction.FinishLoading)
        store.dispatch(MainAction.SuccessAddMovieToUnwanted)
    }

    private suspend fun removeMoviesFromFavorite(
        store: Store<MainState, MainAction>,
        movie: Movie
    ) {
        store.dispatch(MainAction.Loading)

        movieDao.deleteFavorite(movie.toEntity(true, false))
        store.dispatch(MainAction.FinishLoading)
        store.dispatch(MainAction.MovieRemoved)

    }
}