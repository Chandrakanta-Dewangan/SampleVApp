package com.learning.assignment.interactor

import com.google.common.truth.Truth.assertThat
import com.learning.assignment.core.di.FakeClassifiedListingRepository
import com.learning.domain.interactor.GetMovieDetailsUseCase
import com.learning.domain.model.MovieDetails
import com.learning.domain.model.OrderType
import com.learning.domain.model.Result
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private lateinit var fakeRepository: FakeClassifiedListingRepository

    @Before
    fun setUp() {
        fakeRepository = FakeClassifiedListingRepository()
        getMovieDetailsUseCase = GetMovieDetailsUseCase(fakeRepository)
    }

    @Test
    fun `Get_Movie_Details_Not_Empty`() = runBlocking {

        val result = getMovieDetailsUseCase.execute(
            "tt0372784",
            GetMovieDetailsUseCase.Params(OrderType.Ascending)
        )

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val movieResult = result as Result.Success<MovieDetails>

        assertThat(movieResult.data).isNotNull()


    }

    @Test
    fun `Get_Movie_Details_Title`() = runBlocking {

        val result = getMovieDetailsUseCase.execute(
            "tt0372784",
            GetMovieDetailsUseCase.Params(OrderType.Ascending)
        )

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val movieResult = result as Result.Success<MovieDetails>

        assertThat(movieResult.data).isNotNull()

        val movies = movieResult.data

        assertThat(movies).isNotNull()

        assertThat(movies.title).isEqualTo("Batman Begins")

    }

}