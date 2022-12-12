package com.learning.assignment.interactor

import com.google.common.truth.Truth.assertThat
import com.learning.domain.interactor.GetMovieUseCase
import com.learning.assignment.core.di.FakeClassifiedListingRepository
import com.learning.domain.model.Movie
import com.learning.domain.model.OrderType
import com.learning.domain.model.Result
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMovieUseCaseTest {

    private lateinit var getMovieUseCase: GetMovieUseCase
    private lateinit var fakeRepository: FakeClassifiedListingRepository

    @Before
    fun setUp() {
        fakeRepository = FakeClassifiedListingRepository()
        getMovieUseCase = GetMovieUseCase(fakeRepository)
    }

    @Test
    fun `Get_Movie_Count`() = runBlocking {

        val result = getMovieUseCase.execute("Batman", GetMovieUseCase.Params(OrderType.Ascending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val movieResult = result as Result.Success<Movie>

        assertThat(movieResult.data).isNotNull()

        val movies = movieResult.data.movies

        assertThat(movies).isNotNull()

        assertThat(movies).hasSize(2)
    }

    @Test
    fun `Get_Movie_Title`() = runBlocking {

        val result = getMovieUseCase.execute("Batman", GetMovieUseCase.Params(OrderType.Ascending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val movieResult = result as Result.Success<Movie>

        assertThat(movieResult.data).isNotNull()

        val movies = movieResult.data.movies

        assertThat(movies).isNotNull()

        assertThat(movies[0].title).isEqualTo("Batman Begins")

    }

    @Test
    fun `Get_Movie_Sort_By_Name_Ascending`() = runBlocking {

        val result = getMovieUseCase.execute("Batman", GetMovieUseCase.Params(OrderType.Ascending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val movieResult = result as Result.Success<Movie>

        assertThat(movieResult.data).isNotNull()

        val movies = movieResult.data.movies

        assertThat(movies).isNotNull()

        for (i in 0..movies.size - 2) {
            assertThat(movies[i].title).isLessThan(movies[i + 1].title)
        }
    }

    @Test
    fun `Get_Movie_Sort_By_Name_Descending`() = runBlocking {

        val result = getMovieUseCase.execute("Batman", GetMovieUseCase.Params(OrderType.Descending))

        assertThat(result).isInstanceOf(Result.Success::class.java)

        val movieResult = result as Result.Success<Movie>

        assertThat(movieResult.data).isNotNull()

        val movies = movieResult.data.movies

        assertThat(movies).isNotNull()

        for (i in 0..movies.size - 2) {
            assertThat(movies[i].title).isGreaterThan(movies[i + 1].title)
        }
    }
}