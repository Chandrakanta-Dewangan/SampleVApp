package com.learning.assignment.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.learning.assignment.core.utils.FakeDataGenerator
import com.learning.data.core.db.MovieDAO
import com.learning.data.core.db.MovieDatabase
import com.learning.data.core.db.Movies
import com.learning.data.core.db.toRoomMovie
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDatabaseTest {
    private lateinit var db: MovieDatabase
    private lateinit var dao: MovieDAO

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries().build()
        dao = db.movieDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insert_Favorite_Movielist() = runBlocking {
        var movies = FakeDataGenerator.getFakeMovie()
        movies[0].isFav = 1
        dao.insert(movies[0].toRoomMovie())
        val favlist = dao.getAllFavoriteList()
        assertThat(favlist.contains(movies[0].toRoomMovie())).isTrue()
    }
}
