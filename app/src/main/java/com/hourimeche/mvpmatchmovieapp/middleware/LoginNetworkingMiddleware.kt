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
        store: Store<MainViewState, MainAction>,
        movieId: String,
    ) {
        store.dispatch(MainAction.Loading)

        val response = RetrofitClient.instance?.apiService?.getMovieById(
            movieId,
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
        query: String,
    ) {
        store.dispatch(MainAction.Loading)

        val response = RetrofitClient.instance?.apiService?.getMoviesBySearch(
            query,
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