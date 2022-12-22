package com.hourimeche.mvpmatchmovieapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import com.hourimeche.mvpmatchmovieapp.business.domain.middleware.LoggingMiddleware
import com.hourimeche.mvpmatchmovieapp.business.domain.middleware.NetworkingMiddleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    moviesService: MoviesService
) : ViewModel() {
    private val store = Store(
        initialState = MainState(),
        reducer = MainReducer(),
        middlewares = listOf(
            LoggingMiddleware(),
            NetworkingMiddleware(moviesService),
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
}