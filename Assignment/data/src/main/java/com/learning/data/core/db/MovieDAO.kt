package com.learning.data.core.db

import androidx.room.*


@Dao
interface MovieDAO {

    @Query("SELECT * FROM Movies")
    fun getAll(): MutableList<Movies>

    @Query("Delete FROM Movies where imdbID = :id")
    fun deleteMovie(id: String): Int

    @Query("Select * FROM Movies where imdbID = :id")
    fun checkIfMovieIsFavourite(id: String): List<Movies>

    @Query("SELECT * FROM Movies where imdbID = :imdbID")
    fun getMovieById(imdbID: String): List<Movies>?

    @Insert
    fun insert(vararg movie: Movies)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: Movies)

    @Query("SELECT * FROM Movies where isFav = 1")
    fun getAllFavoriteList(): List<Movies>

    @Update
    fun updateMovie(movie: Movies)
}