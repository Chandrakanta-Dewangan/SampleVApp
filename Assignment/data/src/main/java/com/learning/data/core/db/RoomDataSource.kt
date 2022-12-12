package com.learning.data.core.db

import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val movieDao: MovieDAO
) : LocalDataSource {

    override fun getAllFavourites(): List<Movies>? {
        return movieDao.getAllFavoriteList();
    }

    override fun getAll(): List<Movies>? {
        return movieDao.getAll();
    }

    override fun insertThreads(vararg threads: Movies): Int {
        return try {
            movieDao.insert(*threads)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override fun checkIfMovieIsFavourite(id: String): Int {
        var movies = movieDao.checkIfMovieIsFavourite(id)
        println("success:: entry size -->" + movies.size)
        return movies.size
    }

    override fun deleteFavouriteId(id: String): Int {
        return try {
            movieDao.deleteMovie(id)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override fun getImageByImageId(imageId: String): List<Movies>? {
        return movieDao.getMovieById(imageId)
    }

    override fun insertMovie(movies: Movies) {
        return movieDao.insertMovie(movies)
    }

    override fun updateMovie(movies: Movies): Int {
        return try {
            movieDao.updateMovie(movies)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

}