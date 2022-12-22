package com.hourimeche.mvpmatchmovieapp.model

data class SearchResponse(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)