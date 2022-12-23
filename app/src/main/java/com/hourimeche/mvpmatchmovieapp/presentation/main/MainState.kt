package com.hourimeche.mvpmatchmovieapp.presentation.main

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.SearchResponse
import com.hourimeche.mvpmatchmovieapp.business.domain.redux.State

/**
 * An implementation of [State] that describes the configuration of the login screen at a given time.
 */
data class MainState(
    val query: String = "",
    val id: String = "",
    val showProgressBar: Boolean = false,
    val errorMessage: String? = null,
    val moviesResponse: MovieResponse? = null,
    val searchResponse: SearchResponse? = null,
) : State