package com.hourimeche.mvpmatchmovieapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hourimeche.mvpmatchmovieapp.business.domain.middleware.LoggingMiddleware
import com.hourimeche.mvpmatchmovieapp.business.domain.middleware.LoginNetworkingMiddleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val store = Store(
        initialState = MainState(),
        reducer = MainReducer(),
        middlewares = listOf(
            LoggingMiddleware(),
            LoginNetworkingMiddleware(),
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