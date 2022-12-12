package com.learning.data.core.network

import com.learning.data.core.extension.toDate
import com.learning.data.core.network.api.GetMovieDetailsResponse
import com.learning.data.core.network.api.GetMovieResponse
import com.learning.domain.model.Movie
import com.learning.domain.model.MovieDetails
import com.learning.domain.model.Movies
import com.learning.data.core.network.api.Movie as ApiMovie

fun GetMovieResponse.toDomain(): Movie = Movie(
    movies = results.toDomain()
)

fun GetMovieDetailsResponse.toDomain(): MovieDetails = MovieDetails(
    imdbID,
    title,
    year,
    rated,
    plot,
    poster,
    actors,
    0
)

fun List<ApiMovie>.toDomain(): List<Movies> {
    val result = mutableListOf<Movies>()
    this.forEach {
        result.add(it.toDomain())
    }
    return result
}

fun ApiMovie.toDomain(): Movies = Movies(
    imdbID = imdbID,
    title = title,
    year = year,
    poster = poster,
    0
)

