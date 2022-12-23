package com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses

data class SearchResponse(
    val Response: Boolean,
    val Search: List<MovieResponse>?,
    val totalResults: String?
)