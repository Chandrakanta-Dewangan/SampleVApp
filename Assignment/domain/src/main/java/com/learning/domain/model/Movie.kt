package com.learning.domain.model

import java.io.Serializable

data class Movie(
    val movies: List<Movies>
) : Serializable

data class Movies(
    val imdbID: String,
    var title: String,
    var year: String,
    val poster: String,
    var isFav: Int
) : Serializable
