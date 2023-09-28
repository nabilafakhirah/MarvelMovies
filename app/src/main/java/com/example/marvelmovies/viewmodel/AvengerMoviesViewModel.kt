package com.example.marvelmovies.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marvelmovies.data.MovieRepositoryProvider
import com.example.marvelmovies.data.Search
import com.example.marvelmovies.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AvengerMoviesViewModel : ViewModel(), DefaultLifecycleObserver {
    private val movieRepositoryProvider = MovieRepositoryProvider()
    val avengerList: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }
    val marvelList: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }

    init {
        initiateAvengerData()
        initiateMarvelData()
    }

    private fun initiateAvengerData() {
        movieRepositoryProvider.getAvengerMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.Search }
            .subscribe(
                ::populateAvengerList,
                ::onError
            )
    }

    private fun initiateMarvelData() {
        movieRepositoryProvider.getMarvelMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.Search }
            .subscribe(
                ::populateMarvelList,
                ::onError
            )
    }

    private fun initiateTrendingToday() {

    }

    private fun populateAvengerList(list: List<Search>) {
        avengerList.value = list.map {
            Movie(
                title = it.Title,
                year = it.Year,
                imdbId = it.imdbID,
                type = it.Type,
                poster = it.Poster,
            )
        }
    }

    private fun populateMarvelList(list: List<Search>) {
        marvelList.value = list.map {
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