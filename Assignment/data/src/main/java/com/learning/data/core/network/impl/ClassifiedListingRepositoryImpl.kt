package com.learning.data.core.network.impl

import com.learning.data.core.network.dao.ClassifiedListingDao
import com.learning.domain.model.Movie
import com.learning.domain.model.MovieDetails
import com.learning.domain.model.Result
import com.learning.domain.repository.ClassifiedListingRepository
import javax.inject.Inject

class ClassifiedListingRepositoryImpl @Inject constructor(
    private val classifiedListingDao: ClassifiedListingDao
) : ClassifiedListingRepository {

    override suspend fun getMovie(searchString: String): Result<Movie> {
        return classifiedListingDao.getMovie(searchString)
    }

    override suspend fun getMovieDetails(id: String): Result<MovieDetails> {
        return classifiedListingDao.getMovieDetails(id)
    }
}