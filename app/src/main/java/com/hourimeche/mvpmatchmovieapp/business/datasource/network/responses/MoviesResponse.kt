package com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)