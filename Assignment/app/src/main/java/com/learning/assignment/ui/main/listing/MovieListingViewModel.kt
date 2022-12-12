package com.learning.assignment.ui.main.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.learning.domain.interactor.GetMovieUseCase
import com.learning.assignment.ui.main.base.BaseViewModel
import com.learning.data.core.db.RoomDataSource
import com.learning.data.core.db.toDomain
import com.learning.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListingViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val localDataSource: RoomDataSource
) : BaseViewModel() {
    private var sort: OrderType = OrderType.Ascending
    private val _movies: MutableLiveData<List<Movies>> by lazy {
        MutableLiveData<List<Movies>>()
    }
    val movies: LiveData<List<Movies>> get() = _movies

    fun loadData() {
        if (searchString.isEmpty()) {
            favoriteList()
        } else {
            viewModelScope.launch {
                showLoader()
                val result =
                    getMovieUseCase.execute(searchString.trim(), GetMovieUseCase.Params(sort))
                hideLoader()
                when (result) {
                    is Result.Success<Movie> -> {
                        val movies = result.data.movies
                        if (movies.isNotEmpty()) {
                            _movies.postValue(movies)
                        }
                    }
                    is Result.Error -> {
                        result.exception.message?.let { /*showToast(it)*/ }
                    }
                }
            }
        }
    }

    fun favoriteList(): List<Movies> {
        val movies = localDataSource.getAllFavourites()!!.toDomain()
        _movies.postValue(movies)
        return movies
    }

    private var searchString: String = ""
    fun searchData(search: String) {
        searchString = search
        loadData()

    }

    fun sort() {
        sort = when (sort) {
            OrderType.Ascending -> {
                OrderType.Descending
            }
            OrderType.Descending -> {
                OrderType.Ascending
            }
        }
        val currentList = _movies.value!!
        val sortedList = when (sort) {
            OrderType.Ascending -> currentList.sortedBy { it.title }
            OrderType.Descending -> currentList.sortedByDescending { it.title }
        }
        _movies.postValue(sortedList)
    }

}