package com.learning.data.core.db

import com.learning.domain.model.*
import com.learning.data.core.db.Movies as DbMovie

fun Movies.toRoomMovie(): DbMovie =
    DbMovie(
        imdbID,
        title,
        year,
        poster,
        isFav
    )

fun DbMovie.toModelMovie(): Movies =
    Movies(
        imdbID,
        title,
        year,
        poster,
        isFav
    )

fun List<DbMovie>.toDomain(): List<Movies> {
    val result = mutableListOf<Movies>()
    this.forEach {
        result.add(it.toModelMovie())
    }
    return result
}


