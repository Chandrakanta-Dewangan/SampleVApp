package com.learning.domain.interactor

import com.learning.domain.interactor.base.UseCase
import com.learning.domain.model.Movie
import com.learning.domain.model.OrderType
import com.learning.domain.model.Result
import com.learning.domain.repository.ClassifiedListingRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repo: ClassifiedListingRepository
) : UseCase<Result<Movie>, GetMovieUseCase.Params>() {

    override suspend fun execute(searchString: String, params: Params?): Result<Movie> {
        params!!
        return when (val result = repo.getMovie(searchString)) {
            is Result.Error -> {
                result
            }
            is Result.Success<Movie> -> {
                val sortedList = when (params.orderType) {
                    OrderType.Ascending -> result.data.movies.sortedBy { it.title }
                    OrderType.Descending -> result.data.movies.sortedByDescending { it.title }
                }
                Result.Success(Movie(sortedList))
            }
        }
    }

    data class Params(val orderType: OrderType)

}