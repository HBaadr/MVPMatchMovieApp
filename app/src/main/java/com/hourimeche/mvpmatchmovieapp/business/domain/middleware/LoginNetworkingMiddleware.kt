package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import android.util.Log
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.RetrofitClient
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainAction
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainState

class LoginNetworkingMiddleware : Middleware<MainState, MainAction> {

    override suspend fun process(
        action: MainAction,
        currentState: MainState,
        store: Store<MainState, MainAction>,
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
        store: Store<MainState, MainAction>,
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
        store: Store<MainState, MainAction>,
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