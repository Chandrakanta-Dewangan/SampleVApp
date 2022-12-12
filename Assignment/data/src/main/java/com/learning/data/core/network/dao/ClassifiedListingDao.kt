package com.learning.data.core.network.dao

import com.learning.data.core.Constants
import com.learning.data.core.network.api.ClassifiedListingApi
import com.learning.data.core.network.toDomain
import com.learning.domain.model.Movie
import com.learning.domain.model.MovieDetails
import com.learning.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClassifiedListingDao @Inject constructor(
    private val api: ClassifiedListingApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getMovie(searchString: String): Result<Movie> =
        withContext(ioDispatcher) {
            try {
                val result = api.getMovieList(searchString, Constants.API_KEY)
                Result.Success(result.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    suspend fun getMovieDetails(id: String): Result<MovieDetails> =
        withContext(ioDispatcher) {
            try {
                val result = api.getMovieDetails(id, Constants.API_KEY)
                Result.Success(result.toDomain())
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

}
