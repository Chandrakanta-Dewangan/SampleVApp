package com.learning.assignment.utils

import com.learning.domain.model.MovieDetails
import com.learning.domain.model.Movies
import java.util.*

object FakeDataGenerator {
    fun getFakeMovie(): List<Movies> {
        val movies = mutableListOf<Movies>()
        movies.add(
            Movies(
                imdbID = "tt0372784",
                title = "Batman Begins",
                year = "2005",
                poster = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
                0
            )
        )
        movies.add(
            Movies(
                imdbID = "tt2975590",
                title = "Batman v Superman: Dawn of Justice",
                year = "2016",
                poster = "https://m.media-amazon.com/images/M/MV5BYThjYzcyYzItNTVjNy00NDk0LTgwMWQtYjMwNmNlNWJhMzMyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
                0
            )
        )
        return movies
    }


    fun getFakeMovieDetails(): MovieDetails {
        val movieDetails = MovieDetails(
            imdbID = "tt0372784",
            title = "Batman Begins",
            year = "2005",
            rated = "PG-13",
            plot = "Fearing that the actions of Superman are left unchecked, Batman takes on the Man of Steel, while the world wrestles with what kind of a hero it really needs.",
            poster = "https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
            actor = "Ben Affleck, Henry Cavill, Amy Adams",
            0
        )
        return movieDetails
    }


}