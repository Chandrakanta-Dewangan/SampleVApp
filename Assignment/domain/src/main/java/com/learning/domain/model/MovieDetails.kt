package com.learning.domain.model

import java.io.Serializable

data class MovieDetails(
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val plot: String,
    val poster: String,
    val actor: String,
    var isFav: Int
) : Serializable
