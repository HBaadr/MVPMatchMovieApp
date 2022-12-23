package com.hourimeche.mvpmatchmovieapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie.MovieDao
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.middleware.CacheMiddleware
import com.hourimeche.mvpmatchmovieapp.business.domain.middleware.LoggingMiddleware
import com.hourimeche.mvpmatchmovieapp.business.domain.middleware.NetworkingMiddleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    moviesService: MoviesService,
    private val movieDao: MovieDao
) : ViewModel() {
    private val store = Store(
        initialState = MainState(),
        reducer = MainReducer(),
        middlewares = listOf(
            LoggingMiddleware(),
            NetworkingMiddleware(moviesService),
            CacheMiddleware(moviesService, movieDao),
        )
    )

    val viewState: StateFlow<MainState> = store.state

    fun getMovie(movieId: String) {
        val action = MainAction.GetMovie(movieId)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun searchMovies(title: String) {
        val action = MainAction.SearchMovies(title)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun getMoviesFromCache() {
        val action = MainAction.GetMoviesFromCache

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun getUnwantedMoviesFromCache() {
        val action = MainAction.GetUnwantedMoviesFromCache

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun addMovieToCache(movie: MovieResponse) {
        val action = MainAction.AddMovieToCache(movie)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun removeMovieFromCache(movie: MovieResponse) {
        val action = MainAction.RemoveMovieFromCache(movie)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    suspend fun isMovieInFavouriteList(movie: MovieResponse): Boolean {
        return movieDao.isMovieInFavouriteList(movie.imdbID)
    }

    fun addMovieToUnwanted(movie: MovieResponse) {
        val action = MainAction.AddMovieToUnwanted(movie)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}