package com.learning.assignment.core.di

import android.content.Context
import androidx.room.Room
import com.learning.data.core.db.MovieDAO
import com.learning.data.core.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideYourDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context.getApplicationContext(), MovieDatabase::class.java, "movie.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMovieDao(appDatabase: MovieDatabase): MovieDAO {
        return appDatabase.movieDao()
    }
}