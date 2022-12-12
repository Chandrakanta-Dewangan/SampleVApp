package com.learning.domain.interactor

import com.learning.domain.interactor.base.UseCase
import com.learning.domain.model.Movie
import com.learning.domain.model.MovieDetails
import com.learning.domain.model.OrderType
import com.learning.domain.model.Result
import com.learning.domain.repository.ClassifiedListingRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repo: ClassifiedListingRepository
) : UseCase<Result<MovieDetails>, GetMovieDetailsUseCase.Params>() {
    override suspend fun execute(id: String, params: Params?): Result<MovieDetails> {
        params!!
        return when (val result = repo.getMovieDetails(id)) {
            is Result.Error -> {
                result
            }
            is Result.Success<MovieDetails> -> {
                Result.Success(result.data)
            }
        }
    }

    data class Params(val orderType: OrderType)

}