package com.learning.assignment.core.di

import com.learning.domain.model.Result
import com.learning.domain.repository.ClassifiedListingRepository
import com.learning.assignment.core.utils.FakeDataGenerator
import com.learning.domain.model.Movie
import com.learning.domain.model.MovieDetails

class FakeClassifiedListingRepository : ClassifiedListingRepository {
    private val movie = Movie(FakeDataGenerator.getFakeMovie())
    private val movieDetails = FakeDataGenerator.getFakeMovieDetails()

    override suspend fun getMovie(searchString: String): Result<Movie> {
        return Result.Success(movie)
    }

    override suspend fun getMovieDetails(id: String): Result<MovieDetails> {
        return Result.Success(movieDetails)
    }
}