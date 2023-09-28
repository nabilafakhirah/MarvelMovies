package com.example.marvelmovies.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelmovies.data.MovieRepositoryProvider
import com.example.marvelmovies.data.Search
import com.example.marvelmovies.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AvengerMoviesViewModel : ViewModel(), DefaultLifecycleObserver {
    private val movieRepositoryProvider = MovieRepositoryProvider()
    val searchList = mutableStateOf(listOf<Movie>())

    init {
        initiateData()
    }

    private fun initiateData() {
        movieRepositoryProvider.getAvengerMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.Search }
            .subscribe(
                ::populateSearchList,
                ::onError
            )
    }

    private fun populateSearchList(list: List<Search>) {
        searchList.value = list.map {
            Movie(
                title = it.Title,
                year = it.Year,
                imdbId = it.imdbID,
                type = it.Type,
                poster = it.Poster,
            )
        }
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }
}

class AvengerMoviesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AvengerMoviesViewModel() as T
    }
}