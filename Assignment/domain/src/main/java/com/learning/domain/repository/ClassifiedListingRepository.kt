package com.learning.domain.repository

import com.learning.domain.model.Movie
import com.learning.domain.model.MovieDetails
import com.learning.domain.model.Result

interface ClassifiedListingRepository {
    suspend fun getMovie(searchString: String): Result<Movie>
    suspend fun getMovieDetails(id: String): Result<MovieDetails>
}