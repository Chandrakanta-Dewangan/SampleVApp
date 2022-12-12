package com.learning.assignment.ui.main.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.learning.assignment.ui.main.base.BaseViewModel
import com.learning.data.core.db.RoomDataSource
import com.learning.data.core.db.toRoomMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.google.gson.Gson
import com.learning.domain.interactor.GetMovieDetailsUseCase
import com.learning.domain.interactor.GetMovieUseCase
import com.learning.domain.model.*
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val localDataSource: RoomDataSource
) : BaseViewModel() {
    private var sort: OrderType = OrderType.Ascending
    private val _movies: MutableLiveData<MovieDetails> by lazy {
        MutableLiveData<MovieDetails>()
    }
    val movieDetails: LiveData<MovieDetails> get() = _movies

    fun loadData(id: String) {
        viewModelScope.launch {
            showLoader()
            val result = getMovieDetailsUseCase.execute(id, GetMovieDetailsUseCase.Params(sort))
            hideLoader()
            when (result) {
                is Result.Success<MovieDetails> -> {
                    val movies = result.data
                    _movies.postValue(movies)
                }
                is Result.Error -> {
                    result.exception.message?.let { showToast(it) }
                }
            }
        }
    }

    fun insertDeleteFavList(movies: Movies) {
        if (localDataSource.checkIfMovieIsFavourite(movies.imdbID) == 1) {
            localDataSource.deleteFavouriteId(movies.imdbID)
        } else {
            movies.isFav = 1
            localDataSource.insertThreads(movies.toRoomMovie())
        }
    }

    fun isFav(movies: Movies): Int {
        return localDataSource.checkIfMovieIsFavourite(movies.imdbID)
    }

}