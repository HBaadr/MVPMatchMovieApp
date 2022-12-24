package com.hourimeche.mvpmatchmovieapp.business.domain.middleware

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.MoviesService
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Middleware
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.Store
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Constants
import com.hourimeche.mvpmatchmovieapp.business.domain.util.Session
import com.hourimeche.mvpmatchmovieapp.business.domain.util.getGenres
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainAction
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainState

class NetworkingMiddleware(private val moviesService: MoviesService) :
    Middleware<MainState, MainAction> {

    override suspend fun process(
        action: MainAction,
        currentState: MainState,
        store: Store<MainState, MainAction>,
    ) {
        when (action) {
            is MainAction.SearchMovies -> {
                searchMovies(store, action.query)
            }
            else -> {}
        }
    }

    private suspend fun searchMovies(
        store: Store<MainState, MainAction>,
        query: String,
    ) {
        store.dispatch(MainAction.Loading)

        if (Session.TV_GENRES.isEmpty())
            Session.TV_GENRES.addAll(moviesService.getTVGenres().body()!!.genres)
        if (Session.MOVIE_GENRES.isEmpty())
            Session.MOVIE_GENRES.addAll(moviesService.getMovieGenres().body()!!.genres)

        val response = moviesService.getMoviesBySearch(
            query,
            Constants.API_KEY
        )

        val pagesNumber =
            if (response.body()!!.total_pages > 10) 10 else response.body()!!.total_pages
        val allMovies = ArrayList<Movie>()

        for (index in 1..pagesNumber) {
            val rep = moviesService.getMoviesBySearch(
                query,
                page = index
            )
            if (rep.isSuccessful)
                allMovies.addAll(rep.body()!!.results)
        }

        if (response.isSuccessful) {

            val moviesUnwanted = ArrayList<Movie>()
            store.state.value.unwantedMovies?.let { moviesUnwanted.addAll(it) }
            for (movie in ArrayList<Movie>(allMovies)) {
                for (unwanted in moviesUnwanted) {
                    if (movie.id == unwanted.id)
                        allMovies.remove(movie)
                }
                movie.genres = movie.genre_ids.getGenres(movie.media_type?.contains("o")!!)
            }
            store.dispatch(
                MainAction.SuccessSearchMovies(
                    allMovies
                )
            )
        } else {
            store.dispatch(MainAction.Error(response.errorBody().toString()))
        }
    }
}