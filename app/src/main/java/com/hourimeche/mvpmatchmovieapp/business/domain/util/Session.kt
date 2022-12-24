package com.hourimeche.mvpmatchmovieapp.business.domain.util

import com.hourimeche.mvpmatchmovieapp.business.datasource.network.responses.Genre

class Session {
    companion object {
        val TV_GENRES = ArrayList<Genre>()
        val MOVIE_GENRES = ArrayList<Genre>()
    }
}