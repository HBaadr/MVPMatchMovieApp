package com.hourimeche.mvpmatchmovieapp.ui.main

import com.hourimeche.mvpmatchmovieapp.model.MovieResponse
import com.hourimeche.mvpmatchmovieapp.model.SearchResponse
import com.hourimeche.mvpmatchmovieapp.redux.State

/**
 * An implementation of [State] that describes the configuration of the login screen at a given time.
 */
data class MainViewState(
    val query: String = "",
    val id: String = "",
    val showProgressBar: Boolean = false,
    val errorMessage: String? = null,
    val moviesResponse: MovieResponse? = null,
    val searchResponse: SearchResponse? = null,
) : State