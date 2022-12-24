package com.hourimeche.mvpmatchmovieapp.business.domain.util


fun String.firstCap() = this.replaceFirstChar { it.uppercase() }

fun String?.getYear(): String {
    if (this == null || this.length < 4)
        return ""
    return "(" + this.substring(0, 4) + ")"
}

fun List<Int>?.getGenres(isMovie: Boolean): String {
    var genres = ""
    val separator = ", "
    if (this == null)
        return genres
    if (isMovie) {
        if (Session.MOVIE_GENRES.isEmpty())
            return genres
        for (genre in Session.MOVIE_GENRES) {
            if (this.contains(genre.id)) {
                genres += genre.name + separator
            }
        }
    } else {
        if (Session.TV_GENRES.isEmpty())
            return genres
        for (genre in Session.TV_GENRES) {
            if (this.contains(genre.id)) {
                genres += genre.name + separator
            }
        }
    }
    return if (genres.length > 2)
        genres.substring(0, genres.length - 2)
    else
        genres
}