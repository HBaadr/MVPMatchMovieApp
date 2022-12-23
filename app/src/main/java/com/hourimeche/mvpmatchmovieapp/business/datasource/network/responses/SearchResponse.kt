package com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses

data class SearchResponse(
    val Response: String?,
    val Search: List<MovieResponse>?,
    val totalResults: String?
)