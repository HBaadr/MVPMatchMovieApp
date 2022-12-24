package com.hourimeche.mvpmatchmovieapp.business.datasource.cache.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Movie

@Entity(tableName = "movie_properties")
data class MovieEntity(

    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String?,

    @ColumnInfo(name = "media_type")
    val media_type: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    //@ColumnInfo(name = "genre_ids")
    //val genre_ids: List<Int>,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "original_language")
    val original_language: String?,

    @ColumnInfo(name = "original_title")
    val original_title: String?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "popularity")
    val popularity: Double?,

    @ColumnInfo(name = "poster_path")
    val poster_path: String?,

    @ColumnInfo(name = "release_date")
    val release_date: String?,

    @ColumnInfo(name = "first_air_date")
    val first_air_date: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "video")
    val video: Boolean?,

    @ColumnInfo(name = "vote_average")
    val vote_average: Double?,

    @ColumnInfo(name = "vote_count")
    val vote_count: Int?,

    @ColumnInfo(name = "isUnwanted")
    val isUnwanted: Boolean = false,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)

fun MovieEntity.toMovie(): Movie {
    return Movie(
        adult = adult,
        backdrop_path = backdrop_path,
        //genre_ids = genre_ids,
        id = id,
        original_language = original_language,
        media_type = media_type,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        first_air_date = first_air_date,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        name = name,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
    )
}

fun Movie.toEntity(isFavorite: Boolean, isUnwanted: Boolean): MovieEntity {
    return MovieEntity(
        adult = adult,
        backdrop_path = backdrop_path,
        //genre_ids = genre_ids,
        id = id,
        media_type = media_type,
        original_language = original_language,
        name = name,
        first_air_date = first_air_date,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        isFavorite = isFavorite,
        isUnwanted = isUnwanted
    )
}