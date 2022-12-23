package com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.MovieResponse

@Entity(tableName = "movie_properties")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "imdbID")
    val imdbID: String,

    @ColumnInfo(name = "Actors")
    val Actors: String?,

    @ColumnInfo(name = "Awards")
    val Awards: String?,

    @ColumnInfo(name = "BoxOffice")
    val BoxOffice: String?,

    @ColumnInfo(name = "Country")
    val Country: String?,

    @ColumnInfo(name = "DVD")
    val DVD: String?,

    @ColumnInfo(name = "Director")
    val Director: String?,

    @ColumnInfo(name = "Genre")
    val Genre: String?,

    @ColumnInfo(name = "Language")
    val Language: String?,

    @ColumnInfo(name = "Metascore")
    val Metascore: String?,

    @ColumnInfo(name = "Plot")
    val Plot: String?,

    @ColumnInfo(name = "Poster")
    val Poster: String?,

    @ColumnInfo(name = "Production")
    val Production: String?,

    @ColumnInfo(name = "Rated")
    val Rated: String?,

    @ColumnInfo(name = "Released")
    val Released: String?,

    @ColumnInfo(name = "Response")
    val Response: String?,

    @ColumnInfo(name = "Runtime")
    val Runtime: String?,

    @ColumnInfo(name = "Title")
    val Title: String?,

    @ColumnInfo(name = "Type")
    val Type: String?,

    @ColumnInfo(name = "Website")
    val Website: String?,

    @ColumnInfo(name = "Writer")
    val Writer: String?,

    @ColumnInfo(name = "Year")
    val Year: String?,

    @ColumnInfo(name = "imdbRating")
    val imdbRating: String?,

    @ColumnInfo(name = "imdbVotes")
    val imdbVotes: String?,

    @ColumnInfo(name = "isUnwanted")
    val isUnwanted: Boolean = false,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)

fun MovieEntity.toMovieResponse(): MovieResponse {
    return MovieResponse(
        Actors = Actors,
        Awards = Awards,
        BoxOffice = BoxOffice,
        Country = Country,
        DVD = DVD,
        Director = Director,
        Genre = Genre,
        Language = Language,
        Metascore = Metascore,
        Plot = Plot,
        Poster = Poster,
        Production = Production,
        Rated = Rated,
        Released = Released,
        Response = Response,
        Runtime = Runtime,
        Title = Title,
        Type = Type,
        Website = Website,
        Writer = Writer,
        Year = Year,
        imdbID = imdbID,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        Ratings = emptyList()
    )
}

fun MovieResponse.toEntity(isFavorite: Boolean, isUnwanted: Boolean): MovieEntity {
    return MovieEntity(
        Actors = Actors,
        Awards = Awards,
        BoxOffice = BoxOffice,
        Country = Country,
        DVD = DVD,
        Director = Director,
        Genre = Genre,
        Language = Language,
        Metascore = Metascore,
        Plot = Plot,
        Poster = Poster,
        Production = Production,
        Rated = Rated,
        Released = Released,
        Response = Response,
        Runtime = Runtime,
        Title = Title,
        Type = Type,
        Website = Website,
        Writer = Writer,
        Year = Year,
        imdbID = imdbID,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        isFavorite = isFavorite,
        isUnwanted = isUnwanted
    )
}