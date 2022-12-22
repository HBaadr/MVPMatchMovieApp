package com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses

data class Search(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
)