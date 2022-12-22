package com.hourimeche.mvpmatchmovieapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hourimeche.mvpmatchmovieapp.middleware.LoggingMiddleware
import com.hourimeche.mvpmatchmovieapp.middleware.LoginNetworkingMiddleware
import com.hourimeche.mvpmatchmovieapp.redux.Store
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val store = Store(
        initialState = MainViewState(),
        reducer = MainReducer(),
        middlewares = listOf(
            LoggingMiddleware(),
            LoginNetworkingMiddleware(),
        )
    )

    val viewState: StateFlow<MainViewState> = store.state

    fun getMovie(title: String) {
        val action = MainAction.GetMoviesWithTitle(title)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun searchMovies(title: String) {
        val action = MainAction.GetMoviesWithTitle(title)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}