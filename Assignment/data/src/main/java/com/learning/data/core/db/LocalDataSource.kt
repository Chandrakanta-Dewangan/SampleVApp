package com.learning.data.core.db


interface LocalDataSource {
    fun getAll(): List<Movies>?
    fun getAllFavourites(): List<Movies>?
    fun insertThreads(vararg threads: Movies): Int
    fun checkIfMovieIsFavourite(imageId: String): Int
    fun deleteFavouriteId(id: String): Int
    fun getImageByImageId(imageId: String): List<Movies>?
    fun insertMovie(movies: Movies)
    fun updateMovie(movies: Movies): Int

}