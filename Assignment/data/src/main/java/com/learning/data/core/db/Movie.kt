package com.learning.data.core.db

import androidx.room.*
import java.io.Serializable

@Entity
data class Movies(
    @PrimaryKey
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    @ColumnInfo(defaultValue = "0")
    val isFav: Int
) : Serializable