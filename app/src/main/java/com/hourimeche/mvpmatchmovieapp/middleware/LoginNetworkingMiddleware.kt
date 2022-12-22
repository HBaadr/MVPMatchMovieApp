package com.hourimeche.mvpmatchmovieapp.middleware

import android.util.Log
import com.hourimeche.mvpmatchmovieapp.config.Constants
import com.hourimeche.mvpmatchmovieapp.data.RetrofitClient
import com.hourimeche.mvpmatchmovieapp.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.redux.Store
import com.hourimeche.mvpmatchmovieapp.ui.main.MainAction
import com.hourimeche.mvpmatchmovieapp.ui.main.MainViewState

class LoginNetworkingMiddleware : Middleware<MainViewState, MainAction> {

    override suspend fun process(
        action: MainAction,
        currentState: MainViewState,
        store: Store<MainViewState, MainAction>,
    ) {
        when (action) {
            is MainAction.GetMoviesWithTitle -> {
                searchMovies(store, action.title)
            }
            else -> {}
        }
    }

    private suspend fun searchTitle(
        store: Store<MainViewState, MainAction>,
        title: String,
    ) {
        store.dispatch(MainAction.Loading)

        val response = RetrofitClient.instance?.apiService?.getMovieByTitle(
            title,
            Constants.API_KEY
        )
        Log.d("LoggingMiddleware", response.toString())
        if (response == null) {
            store.dispatch(MainAction.Error("Response null"))
        } else if (response.isSuccessful) {
            store.dispatch(MainAction.Success(response.body()!!))
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }
    }

    private suspend fun searchMovies(
        store: Store<MainViewState, MainAction>,
        title: String,
    ) {
        store.dispatch(MainAction.Loading)

        val response = RetrofitClient.instance?.apiService?.getMoviesBySearch(
            title,
            Constants.API_KEY
        )
        Log.d("LoggingMiddleware", response.toString())
        if (response == null) {
            store.dispatch(MainAction.Error("Response null"))
        } else if (response.isSuccessful) {
            store.dispatch(MainAction.SuccessMovies(response.body()!!))
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }
    }
}